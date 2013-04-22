package org.rnd.jmagic.abilities.keywords;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

public final class Reinforce extends Keyword
{
	private final SetGenerator numCounters;
	private final String numCountersName;
	private final String cost;

	public Reinforce(GameState state, SetGenerator numCounters, String numCountersName, String cost)
	{
		super(state, "Reinforce " + numCountersName + " - " + new ManaPool(cost).toString());
		this.numCounters = numCounters;
		this.numCountersName = numCountersName;
		this.cost = cost;
	}

	@Override
	public Reinforce create(Game game)
	{
		return new Reinforce(game.physicalState, this.numCounters, this.numCountersName, this.cost);
	}

	@Override
	protected java.util.List<NonStaticAbility> createNonStaticAbilities()
	{
		java.util.List<NonStaticAbility> ret = new java.util.LinkedList<NonStaticAbility>();

		ret.add(new ReinforceAbility(this.state, this.numCounters, this.numCountersName, this.cost));

		return ret;
	}

	public static final class ReinforceAbility extends ActivatedAbility
	{
		private final SetGenerator numCounters;
		private final String numCountersName;
		private final String cost;

		public ReinforceAbility(GameState state, SetGenerator numCounters, String numCountersName, String cost)
		{
			super(state, new ManaPool(cost).toString() + ", Discard this card: Put " + numCountersName + " +1/+1 counters on target creature.");

			this.numCounters = numCounters;
			this.numCountersName = numCountersName;
			this.cost = cost;

			this.setManaCost(new ManaPool(cost));

			SetGenerator thisCard = ABILITY_SOURCE_OF_THIS;

			EventFactory costFactory = new EventFactory(EventType.DISCARD_CARDS, "Discard this card");
			costFactory.parameters.put(EventType.Parameter.CAUSE, This.instance());
			costFactory.parameters.put(EventType.Parameter.CARD, thisCard);
			this.addCost(costFactory);

			Target target = this.addTarget(CreaturePermanents.instance(), "target creature");
			this.addEffect(putCounters(numCounters, Counter.CounterType.PLUS_ONE_PLUS_ONE, targetedBy(target), "Put " + numCountersName + " +1/+1 counters on target creature."));

			this.activateOnlyFromHand();
		}

		@Override
		public ReinforceAbility create(Game game)
		{
			return new ReinforceAbility(game.physicalState, this.numCounters, this.numCountersName, this.cost);
		}
	}
}
