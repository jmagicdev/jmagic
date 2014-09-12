package org.rnd.jmagic.engine;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.generators.*;

public abstract class LoyaltyAbility extends ActivatedAbility
{
	public static enum OtherCost
	{
		MINUS_X
		{
			@Override
			public String toString()
			{
				return "-X";
			}
		}
	}

	public LoyaltyAbility(GameState state, int loyaltyCost, String effectName)
	{
		super(state, (loyaltyCost > 0 ? "+" : "") + loyaltyCost + ": " + effectName);

		SetGenerator thisCard = AbilitySource.instance(This.instance());

		EventFactory factory = new EventFactory(((loyaltyCost < 0) ? EventType.REMOVE_COUNTERS : EventType.PUT_COUNTERS), (((loyaltyCost > 0) ? "+" : "") + loyaltyCost));
		factory.parameters.put(EventType.Parameter.CAUSE, This.instance());
		factory.parameters.put(EventType.Parameter.COUNTER, Identity.instance(Counter.CounterType.LOYALTY));
		factory.parameters.put(EventType.Parameter.NUMBER, numberGenerator(Math.abs(loyaltyCost)));
		factory.parameters.put(EventType.Parameter.OBJECT, thisCard);
		this.addCost(factory);

		// 606.5 An ability with a negative loyalty cost can't be activated
		// unless the permanent has at least that many loyalty counters on
		// it.
		if(loyaltyCost < 0)
		{
			SetGenerator lessThanRequiredLoyalty = Between.instance(null, -loyaltyCost - 1);
			SetGenerator loyaltyCounters = CountersOn.instance(thisCard, Counter.CounterType.LOYALTY);
			SetGenerator thisDoesntHaveRequiredLoyalty = Intersect.instance(Count.instance(loyaltyCounters), lessThanRequiredLoyalty);
			this.addActivateRestriction(thisDoesntHaveRequiredLoyalty);
		}

		this.setUpLoyaltyRestrictions(state);
	}

	public LoyaltyAbility(GameState state, OtherCost cost, String effectName)
	{
		super(state, cost + ": " + effectName);

		SetGenerator thisCard = AbilitySource.instance(This.instance());
		SetGenerator X = ValueOfX.instance(This.instance());
		switch(cost)
		{
		case MINUS_X:
			EventFactory factory = new EventFactory(EventType.REMOVE_COUNTERS, "-X");
			factory.parameters.put(EventType.Parameter.CAUSE, This.instance());
			factory.parameters.put(EventType.Parameter.COUNTER, Identity.instance(Counter.CounterType.LOYALTY));
			factory.parameters.put(EventType.Parameter.NUMBER, X);
			factory.parameters.put(EventType.Parameter.OBJECT, thisCard);
			factory.usesX();
			this.addCost(factory);
		}

		this.setUpLoyaltyRestrictions(state);
	}

	/**
	 * 605.1a An activated ability is a mana ability if it meets three criteria:
	 * it doesn't have a target, it could put mana into a player's mana pool
	 * when it resolves, and it's not a loyalty ability.
	 * 
	 * @return false
	 */
	@Override
	public boolean isManaAbility()
	{
		return false;
	}

	private void setUpLoyaltyRestrictions(GameState state)
	{
		this.activateOnlyAtSorcerySpeed();
		this.addActivateRestriction(Intersect.instance(LoyaltyUsedUpThisTurn.instance(), AbilitySource.instance(This.instance())));
		state.ensureTracker(new LoyaltyUsedUpThisTurn.Counter());
	}
}
