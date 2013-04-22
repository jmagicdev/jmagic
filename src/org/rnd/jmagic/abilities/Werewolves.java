package org.rnd.jmagic.abilities;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

public class Werewolves
{
	/**
	 * keys are player IDs, values are the number of spells the player cast in
	 * the previous turn
	 */
	public static final class Tracker extends org.rnd.jmagic.engine.Tracker<java.util.Map<Integer, Integer>>
	{
		private java.util.HashMap<Integer, Integer> thisTurn = new java.util.HashMap<Integer, Integer>();
		private java.util.HashMap<Integer, Integer> previousTurn = new java.util.HashMap<Integer, Integer>();
		private java.util.Map<Integer, Integer> unmodifiable = java.util.Collections.unmodifiableMap(this.previousTurn);

		@SuppressWarnings("unchecked")
		@Override
		public Tracker clone()
		{
			Tracker ret = (Tracker)super.clone();
			ret.thisTurn = (java.util.HashMap<Integer, Integer>)this.thisTurn.clone();
			ret.previousTurn = (java.util.HashMap<Integer, Integer>)this.previousTurn.clone();
			ret.unmodifiable = java.util.Collections.unmodifiableMap(ret.previousTurn);
			return ret;
		}

		@Override
		protected boolean match(GameState state, Event event)
		{
			if(event.type == EventType.CAST_SPELL_OR_ACTIVATE_ABILITY)
			{
				if(event.getResult(state).getOne(GameObject.class).isSpell())
					return true;
			}
			else if(event.type == EventType.BEGIN_TURN)
				return true;
			return false;
		}

		@Override
		protected java.util.Map<Integer, Integer> getValueInternal()
		{
			return this.unmodifiable;
		}

		@Override
		protected void update(GameState state, Event event)
		{
			if(event.type == EventType.CAST_SPELL_OR_ACTIVATE_ABILITY)
			{
				Player caster = event.parameters.get(EventType.Parameter.PLAYER).evaluate(state, null).getOne(Player.class);
				if(this.thisTurn.containsKey(caster.ID))
					this.thisTurn.put(caster.ID, this.thisTurn.get(caster.ID) + 1);
				else
					this.thisTurn.put(caster.ID, 1);
			}
			else
			// it's a turn change
			{
				this.previousTurn.clear();
				this.previousTurn.putAll(this.thisTurn);
				this.thisTurn.clear();
			}
		}

		@Override
		protected void reset()
		{
			// This is an actively resetting tracker.
		}
	}

	public static final class FullMoon extends SetGenerator
	{
		private static SetGenerator _instance;

		private FullMoon()
		{
			// singleton generator
		}

		public static SetGenerator instance()
		{
			if(_instance == null)
				_instance = new FullMoon();
			return _instance;
		}

		@Override
		public Set evaluate(GameState state, Identified thisObject)
		{
			Tracker tracker = state.getTracker(Tracker.class);
			if(tracker.getValue(state).isEmpty())
				return NonEmpty.set;
			return Empty.set;
		}
	}

	public static final class NewMoon extends SetGenerator
	{
		private static SetGenerator _instance;

		private NewMoon()
		{
			// singleton generator
		}

		public static SetGenerator instance()
		{
			if(_instance == null)
				_instance = new NewMoon();
			return _instance;
		}

		@Override
		public Set evaluate(GameState state, Identified thisObject)
		{
			Tracker tracker = state.getTracker(Tracker.class);

			// if "a player" cast two or more spells this turn -- a single value
			// in the map must be >= 2
			for(int spellCount: tracker.getValue(state).values())
				if(spellCount >= 2)
					return NonEmpty.set;
			return Empty.set;
		}
	}

	public static final class BecomeFuzzy extends EventTriggeredAbility
	{
		private final String cardName;

		public BecomeFuzzy(GameState state, String cardName)
		{
			super(state, "At the beginning of each upkeep, if no spells were cast last turn, transform " + cardName + ".");
			this.cardName = cardName;

			this.addPattern(atTheBeginningOfEachUpkeep());

			state.ensureTracker(new Tracker());
			this.interveningIf = FullMoon.instance();

			this.addEffect(transformThis(cardName));
		}

		@Override
		public BecomeFuzzy create(Game game)
		{
			return new BecomeFuzzy(game.physicalState, this.cardName);
		}
	}

	public static final class BecomeHuman extends EventTriggeredAbility
	{
		private final String cardName;

		public BecomeHuman(GameState state, String cardName)
		{
			super(state, "At the beginning of each upkeep, if a player cast two or more spells last turn, transform " + cardName + ".");
			this.cardName = cardName;

			this.addPattern(atTheBeginningOfEachUpkeep());

			state.ensureTracker(new Tracker());
			this.interveningIf = NewMoon.instance();

			this.addEffect(transformThis(cardName));
		}

		@Override
		public BecomeHuman create(Game game)
		{
			return new BecomeHuman(game.physicalState, this.cardName);
		}
	}
}