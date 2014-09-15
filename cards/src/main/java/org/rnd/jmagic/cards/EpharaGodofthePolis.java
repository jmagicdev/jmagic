package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Ephara, God of the Polis")
@SuperTypes({SuperType.LEGENDARY})
@Types({Type.ENCHANTMENT, Type.CREATURE})
@SubTypes({SubType.GOD})
@ManaCost("2WU")
@ColorIdentity({Color.WHITE, Color.BLUE})
public final class EpharaGodofthePolis extends Card
{
	public static final class EpharaGodofthePolisAbility1 extends StaticAbility
	{
		public EpharaGodofthePolisAbility1(GameState state)
		{
			super(state, "As long as your devotion to white and blue is less than seven, Ephara isn't a creature.");

			SetGenerator notEnoughDevotion = Intersect.instance(Between.instance(null, 6), DevotionTo.instance(Color.WHITE, Color.BLUE));
			this.canApply = Both.instance(this.canApply, notEnoughDevotion);

			ContinuousEffect.Part part = new ContinuousEffect.Part(ContinuousEffectType.REMOVE_TYPES);
			part.parameters.put(ContinuousEffectType.Parameter.OBJECT, This.instance());
			part.parameters.put(ContinuousEffectType.Parameter.TYPE, Identity.instance(Type.CREATURE));
			this.addEffectPart(part);
		}
	}

	/**
	 * player -> creatures they had enter the battlefield last turn
	 */
	public static final class CreatureFallTracker extends Tracker<java.util.Map<Integer, java.util.Set<Integer>>>
	{
		private java.util.Map<Integer, java.util.Set<Integer>> thisTurn = new java.util.HashMap<>();
		private java.util.Map<Integer, java.util.Set<Integer>> previousTurn = new java.util.HashMap<>();
		private java.util.Map<Integer, java.util.Set<Integer>> unmodifiable = java.util.Collections.unmodifiableMap(this.previousTurn);

		@Override
		protected CreatureFallTracker clone()
		{
			CreatureFallTracker ret = (CreatureFallTracker)super.clone();
			ret.thisTurn = new java.util.HashMap<>(this.thisTurn);
			ret.previousTurn = new java.util.HashMap<>(this.previousTurn);
			ret.unmodifiable = java.util.Collections.unmodifiableMap(ret.previousTurn);
			return ret;
		}

		@Override
		protected boolean match(GameState state, Event event)
		{
			if(event.type == EventType.BEGIN_TURN)
				return true;
			if(event.type != EventType.MOVE_BATCH)
				return false;
			for(ZoneChange change: event.parametersNow.get(EventType.Parameter.TARGET).evaluate(state, null).getAll(ZoneChange.class))
				if(state.battlefield().ID == change.destinationZoneID)
					return true;
			return false;
		}

		@Override
		protected java.util.Map<Integer, java.util.Set<Integer>> getValueInternal()
		{
			return this.unmodifiable;
		}

		@Override
		protected void reset()
		{
			// actively resetting tracker
		}

		@Override
		protected void update(GameState state, Event event)
		{
			if(event.type == EventType.BEGIN_TURN)
			{
				this.previousTurn.clear();
				this.previousTurn.putAll(this.thisTurn);
				this.thisTurn.clear();
				return;
			}

			for(ZoneChange change: event.parametersNow.get(EventType.Parameter.TARGET).evaluate(state, null).getAll(ZoneChange.class))
			{
				GameObject object = state.<GameObject>get(change.newObjectID);
				if(!object.getTypes().contains(Type.CREATURE))
					continue;
				if(!this.thisTurn.containsKey(change.controllerID))
					this.thisTurn.put(change.controllerID, new java.util.HashSet<>());
				this.thisTurn.get(change.controllerID).add(change.newObjectID);
			}
		}
	}

	public static final class YouHadAnotherCreatureDropLastTurn extends SetGenerator
	{
		private YouHadAnotherCreatureDropLastTurn()
		{
			// singleton
		}

		private static SetGenerator _instance = null;

		public static SetGenerator instance()
		{
			if(_instance == null)
				_instance = new YouHadAnotherCreatureDropLastTurn();
			return _instance;
		}

		public Set evaluate(GameState state, Identified thisObject)
		{
			Player you = You.instance().evaluate(state, thisObject).getOne(Player.class);
			java.util.Map<Integer, java.util.Set<Integer>> flag = state.getTracker(CreatureFallTracker.class).getValue(state);
			if(!flag.containsKey(you.ID))
				return Empty.set;

			GameObject thisCreature = ABILITY_SOURCE_OF_THIS.evaluate(state, thisObject).getOne(GameObject.class);
			java.util.Set<Integer> creatures = flag.get(you.ID);
			for(int creatureID: creatures)
				if(creatureID != thisCreature.ID)
					return NonEmpty.set;

			return Empty.set;
		}
	}

	public static final class EpharaGodofthePolisAbility2 extends EventTriggeredAbility
	{
		public EpharaGodofthePolisAbility2(GameState state)
		{
			super(state, "At the beginning of each upkeep, if you had another creature enter the battlefield under your control last turn, draw a card.");
			this.addPattern(atTheBeginningOfEachUpkeep());

			state.ensureTracker(new CreatureFallTracker());
			this.interveningIf = YouHadAnotherCreatureDropLastTurn.instance();

			this.addEffect(drawACard());
		}
	}

	public EpharaGodofthePolis(GameState state)
	{
		super(state);

		this.setPower(6);
		this.setToughness(5);

		// Indestructible
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Indestructible(state));

		// As long as your devotion to white and blue is less than seven, Ephara
		// isn't a creature.
		this.addAbility(new EpharaGodofthePolisAbility1(state));

		// At the beginning of each upkeep, if you had another creature enter
		// the battlefield under your control last turn, draw a card.
		this.addAbility(new EpharaGodofthePolisAbility2(state));
	}
}
