package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("The Chain Veil")
@SuperTypes({SuperType.LEGENDARY})
@Types({Type.ARTIFACT})
@ManaCost("4")
@ColorIdentity({})
public final class TheChainVeil extends Card
{
	/**
	 * IDs of players who have activated a loyalty ability of a planeswalker.
	 */
	public static class PlayerTracker extends Tracker<java.util.Set<Integer>>
	{
		private java.util.HashSet<Integer> values = new java.util.HashSet<>();
		private java.util.Set<Integer> unmodifiable = java.util.Collections.unmodifiableSet(this.values);

		@SuppressWarnings("unchecked")
		@Override
		public PlayerTracker clone()
		{
			PlayerTracker ret = (PlayerTracker)super.clone();
			ret.values = (java.util.HashSet<Integer>)this.values.clone();
			ret.unmodifiable = java.util.Collections.unmodifiableSet(ret.values);
			return ret;
		}

		@Override
		protected java.util.Set<Integer> getValueInternal()
		{
			return this.unmodifiable;
		}

		@Override
		protected boolean match(GameState state, Event event)
		{
			if(event.type == EventType.CAST_SPELL_OR_ACTIVATE_ABILITY)
			{
				GameObject ability = event.getResult(state).getOne(GameObject.class);
				if(ability.isActivatedAbility())
					for(EventFactory cost: ability.getCosts())
						if(cost.type == EventType.REMOVE_COUNTERS || cost.type == EventType.PUT_COUNTERS)
						{
							java.util.Set<org.rnd.jmagic.engine.Counter.CounterType> counterTypes = //
							cost.parameters.get(EventType.Parameter.COUNTER)//
							.evaluate(state, ability).getAll(org.rnd.jmagic.engine.Counter.CounterType.class);
							if(counterTypes.size() == 1 && counterTypes.contains(org.rnd.jmagic.engine.Counter.CounterType.LOYALTY))
								return true;
						}
			}

			return false;
		}

		@Override
		protected void reset()
		{
			this.values.clear();
		}

		@Override
		protected void update(GameState state, Event event)
		{
			this.values.add(event.parameters.get(EventType.Parameter.PLAYER).evaluate(state, null).getOne(Player.class).ID);
		}
	}

	public static final class YouDidntActivateLoyalty extends SetGenerator
	{
		private YouDidntActivateLoyalty()
		{
			// singleton
		}

		private static SetGenerator _instance = null;

		public static SetGenerator instance()
		{
			if(_instance == null)
				_instance = new YouDidntActivateLoyalty();
			return _instance;
		}

		public Set evaluate(GameState state, Identified thisObject)
		{
			int you = You.instance().evaluate(state, thisObject).getOne(Player.class).ID;
			if(state.getTracker(PlayerTracker.class).getValue(state).contains(you))
				return Empty.set;
			return NonEmpty.set;
		}
	}

	public static final class TheChainVeilAbility0 extends EventTriggeredAbility
	{
		public TheChainVeilAbility0(GameState state)
		{
			super(state, "At the beginning of your end step, if you didn't activate a loyalty ability of a planeswalker this turn, you lose 2 life.");
			this.addPattern(atTheBeginningOfYourEndStep());

			state.ensureTracker(new PlayerTracker());
			this.interveningIf = YouDidntActivateLoyalty.instance();

			this.addEffect(loseLife(You.instance(), 2, "You lose 2 life."));
		}
	}

	public static final class TheChainVeilAbility1 extends ActivatedAbility
	{
		public TheChainVeilAbility1(GameState state)
		{
			super(state, "(4), (T): For each planeswalker you control, you may activate one of its loyalty abilities once this turn as though none of its loyalty abilities have been activated this turn.");
			this.setManaCost(new ManaPool("(4)"));
			this.costsTap = true;

			SetGenerator yourPlaneswalkers = Intersect.instance(ControlledBy.instance(You.instance()), HasType.instance(Type.PLANESWALKER));

			ContinuousEffect.Part part = new ContinuousEffect.Part(ContinuousEffectType.ACTIVATE_ADDITIONAL_LOYALTY_ABILITY);
			part.parameters.put(ContinuousEffectType.Parameter.OBJECT, yourPlaneswalkers);
			this.addEffect(createFloatingEffect("For each planeswalker you control, you may activate one of its loyalty abilities once this turn as though none of its loyalty abilities have been activated this turn.", part));
		}
	}

	public TheChainVeil(GameState state)
	{
		super(state);

		// At the beginning of your end step, if you didn't activate a loyalty
		// ability of a planeswalker this turn, you lose 2 life.
		this.addAbility(new TheChainVeilAbility0(state));

		// (4), (T): For each planeswalker you control, you may activate one of
		// its loyalty abilities once this turn as though none of its loyalty
		// abilities have been activated this turn.
		this.addAbility(new TheChainVeilAbility1(state));
	}
}
