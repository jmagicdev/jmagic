package org.rnd.jmagic.engine.generators;

import org.rnd.jmagic.engine.*;

/**
 * If "this permanent" was not kicked, evaluates to empty. Otherwise evaluates
 * to the number of times it was kicked.
 *
 * "This permanent" is considered to be kicked if the spell that became it was
 * kicked.
 */
public class ThisPermanentWasKicked extends SetGenerator
{
	public static ThisPermanentWasKicked instance(CostCollection cost)
	{
		return new ThisPermanentWasKicked(cost);
	}

	private CostCollection cost;

	private ThisPermanentWasKicked(CostCollection cost)
	{
		this.cost = cost;
	}

	@Override
	public Set evaluate(GameState state, Identified thisObject)
	{
		if(!(thisObject.isGameObject()))
			return Empty.set;
		if(thisObject.isActivatedAbility() || thisObject.isTriggeredAbility())
			thisObject = ((NonStaticAbility)thisObject).getSource(state);

		int pastSelf = ((GameObject)thisObject).pastSelf;
		int timesKicked = 0;
		if(pastSelf != -1)
		{
			GameObject spell = state.get(pastSelf);
			for(java.util.Collection<CostCollection> costs: spell.getOptionalAdditionalCostsChosen())
				for(CostCollection cost: costs)
					if(this.cost.equals(cost))
						timesKicked++;
		}

		if(timesKicked == 0)
			return Empty.set;
		return new Set.Unmodifiable(timesKicked);
	}
}
