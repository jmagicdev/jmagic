package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.engine.patterns.*;

@Name("Serra Avenger")
@Types({Type.CREATURE})
@SubTypes({SubType.ANGEL})
@ManaCost("WW")
@ColorIdentity({Color.WHITE})
public final class SerraAvenger extends Card
{
	public static final class TurnTracker extends Tracker<java.util.Map<Integer, Integer>>
	{
		private java.util.HashMap<Integer, Integer> counts = new java.util.HashMap<Integer, Integer>();
		private java.util.Map<Integer, Integer> unmodifiable = java.util.Collections.unmodifiableMap(this.counts);

		@SuppressWarnings("unchecked")
		@Override
		public TurnTracker clone()
		{
			TurnTracker ret = (TurnTracker)super.clone();
			ret.counts = (java.util.HashMap<Integer, Integer>)this.counts.clone();
			ret.unmodifiable = java.util.Collections.unmodifiableMap(ret.counts);
			return ret;
		}

		@Override
		protected java.util.Map<Integer, Integer> getValueInternal()
		{
			return this.unmodifiable;
		}

		@Override
		protected void reset()
		{
			// do nothing
		}

		@Override
		protected boolean match(GameState state, Event event)
		{
			return event.type == EventType.BEGIN_TURN;
		}

		@Override
		protected void update(GameState state, Event event)
		{
			Turn turn = event.parametersNow.get(EventType.Parameter.TURN).evaluate(state, null).getOne(Turn.class);
			Player owner = turn.getOwner(state);
			if(this.counts.containsKey(owner.ID))
				this.counts.put(owner.ID, this.counts.get(owner.ID) + 1);
			else
				this.counts.put(owner.ID, 1);
		}
	}

	public static final class PlayersNotPastThirdTurn extends SetGenerator
	{
		private static PlayersNotPastThirdTurn _instance = null;

		public static PlayersNotPastThirdTurn instance()
		{
			if(_instance == null)
				_instance = new PlayersNotPastThirdTurn();
			return _instance;
		}

		private PlayersNotPastThirdTurn()
		{
			// singleton constructor
		}

		@Override
		public Set evaluate(GameState state, Identified thisObject)
		{
			Set ret = new Set();
			java.util.Map<Integer, Integer> tracker = state.getTracker(TurnTracker.class).getValue(state);

			for(Player player: state.players)
				if(!tracker.containsKey(player.ID) || tracker.get(player.ID) <= 3)
					ret.add(player);

			return ret;
		}
	}

	public static final class SerraAvengerAbility0 extends StaticAbility
	{
		public SerraAvengerAbility0(GameState state)
		{
			super(state, "You can't cast Serra Avenger during your first, second, or third turns of the game.");

			state.ensureTracker(new TurnTracker());
			SimpleEventPattern castSpell = new SimpleEventPattern(EventType.CAST_SPELL_OR_ACTIVATE_ABILITY);
			castSpell.put(EventType.Parameter.PLAYER, PlayersNotPastThirdTurn.instance());
			castSpell.put(EventType.Parameter.OBJECT, This.instance());

			ContinuousEffect.Part part = new ContinuousEffect.Part(ContinuousEffectType.PROHIBIT);
			part.parameters.put(ContinuousEffectType.Parameter.PROHIBITION, Identity.instance(castSpell));
			this.addEffectPart(part);

			this.canApply = NonEmpty.instance();
		}
	}

	public SerraAvenger(GameState state)
	{
		super(state);

		this.setPower(3);
		this.setToughness(3);

		// You can't cast Serra Avenger during your first, second, or third
		// turns of the game.
		this.addAbility(new SerraAvengerAbility0(state));

		// Flying
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Flying(state));

		// Vigilance (Attacking doesn't cause this creature to tap.)
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Vigilance(state));
	}
}
