package org.rnd.jmagic.abilities.keywords;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

public abstract class CyclingBase extends Keyword
{
	public CyclingBase(GameState state, String name)
	{
		super(state, name);
	}

	@Override
	protected final java.util.List<NonStaticAbility> createNonStaticAbilities()
	{
		return new java.util.LinkedList<NonStaticAbility>(this.createCyclingAbilities(this.state));
	}

	protected abstract java.util.List<CyclingAbilityBase<?>> createCyclingAbilities(GameState state);

	/**
	 * Child classes will use this to specify what the cost is
	 */
	protected abstract CostCollection getCost();

	public static abstract class CyclingAbilityBase<C extends CyclingBase> extends ActivatedAbility
	{
		protected final int parentID;

		public CyclingAbilityBase(GameState state, C parent, String name)
		{
			super(state, name);

			this.parentID = parent.ID;

			CostCollection cost = parent.getCost();
			this.setManaCost(cost.manaCost);
			for(EventFactory factory: cost.events)
				this.addCost(factory);

			SetGenerator thisCard = ABILITY_SOURCE_OF_THIS;

			EventFactory discardFactory = new EventFactory(EventType.DISCARD_CARDS, "Discard this");
			discardFactory.parameters.put(EventType.Parameter.CAUSE, This.instance());
			discardFactory.parameters.put(EventType.Parameter.CARD, thisCard);
			this.addCost(discardFactory);

			this.activateOnlyFromHand();
		}
	}
}
