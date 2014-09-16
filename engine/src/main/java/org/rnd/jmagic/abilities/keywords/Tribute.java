package org.rnd.jmagic.abilities.keywords;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

public final class Tribute extends Keyword
{
	public static final PlayerInterface.ChooseReason REASON = new PlayerInterface.ChooseReason("Tribute", "Choose an opponent who will decide whether to put +1/+1 counters on this creature.", true);

	public static class TributeTracker extends Tracker<java.util.Set<Integer>>
	{
		private java.util.Set<Integer> values = new java.util.HashSet<Integer>();
		private java.util.Set<Integer> unmodifiable = java.util.Collections.unmodifiableSet(this.values);

		@Override
		protected TributeTracker clone()
		{
			TributeTracker ret = (TributeTracker)super.clone();
			ret.values = new java.util.HashSet<Integer>(this.values);
			ret.unmodifiable = java.util.Collections.unmodifiableSet(ret.values);
			return ret;
		}

		@Override
		protected java.util.Set<Integer> getValueInternal()
		{
			return this.unmodifiable;
		}

		@Override
		protected void reset()
		{
			// purposefully non-functional.
		}

		@Override
		protected boolean match(GameState state, Event event)
		{
			return event.type == EventType.TRIBUTE_PAID;
		}

		@Override
		protected void update(GameState state, Event event)
		{
			for(GameObject object: event.getResult().getAll(GameObject.class))
				this.values.add(object.ID);
		}
	}

	/**
	 * Use this generator as the intervening if for any triggered ability that
	 * says 'if tribute wasn't paid'. It will find the source of this ability
	 * and determine if tribute was paid. If it wasn't, this generator returns
	 * non-empty.
	 */
	public static class WasntPaid extends SetGenerator
	{
		private WasntPaid()
		{
			// singleton generator
		}

		private static SetGenerator _instance = new WasntPaid();

		public static SetGenerator instance()
		{
			return _instance;
		}

		@Override
		public Set evaluate(GameState state, Identified thisObject)
		{
			int sourceID = ((NonStaticAbility)thisObject).getSourceID();
			if(state.getTracker(TributeTracker.class).getValue(state).contains(sourceID))
				return Empty.set;
			return NonEmpty.set;
		}
	}

	private final int N;

	public Tribute(GameState state, int N)
	{
		super(state, "Tribute");
		this.N = N;
	}

	@Override
	public Tribute create(Game game)
	{
		return new Tribute(game.physicalState, this.N);
	}

	@Override
	protected java.util.List<StaticAbility> createStaticAbilities()
	{
		java.util.List<StaticAbility> ret = new java.util.LinkedList<StaticAbility>();
		ret.add(new PutCounter(this.state, this));
		return ret;
	}

	public static final class PutCounter extends StaticAbility
	{
		private Tribute parent;

		public PutCounter(GameState state, Tribute parent)
		{
			super(state, "As this enters the battlefield, choose an opponent. That opponent may have this creature enter the battlefield with an additional " + org.rnd.util.NumberNames.get(parent.N, "") + " +1/+1 counter" + (parent.N == 1 ? "" : "s") + " on it.");
			this.parent = parent;
			state.ensureTracker(new TributeTracker());

			ZoneChangeReplacementEffect replacement = new ZoneChangeReplacementEffect(this.game, this.getName());
			replacement.addPattern(asThisEntersTheBattlefield());

			EventFactory chooseOpponent = playerChoose(You.instance(), 1, OpponentsOf.instance(You.instance()), PlayerInterface.ChoiceType.PLAYER, REASON, "Choose an opponent.");
			replacement.addEffect(chooseOpponent);

			SetGenerator thatOpponent = EffectResult.instance(chooseOpponent);

			SetGenerator thisObject = NewObjectOf.instance(ReplacedBy.instance(Identity.instance(replacement)));
			String withCounters = "with an additional " + org.rnd.util.NumberNames.get(parent.N, "") + " +1/+1 counter" + (parent.N == 1 ? "" : "s") + " on it.";
			EventFactory putCounters = putCounters(parent.N, Counter.CounterType.PLUS_ONE_PLUS_ONE, thisObject, "This enters the battlefield " + withCounters);

			EventFactory tributePaid = new EventFactory(EventType.TRIBUTE_PAID, "Opponent paid tribute to this creature.");
			tributePaid.parameters.put(EventType.Parameter.OBJECT, thisObject);

			EventFactory mayPutCounters = playerMay(thatOpponent, sequence(putCounters, tributePaid), "That opponent may have this creature enter the battlefield " + withCounters);
			replacement.addEffect(mayPutCounters);

			this.addEffectPart(replacementEffectPart(replacement));
			this.canApply = NonEmpty.instance();
		}

		@Override
		public PutCounter create(Game game)
		{
			return new PutCounter(game.physicalState, this.parent);
		}
	}
}
