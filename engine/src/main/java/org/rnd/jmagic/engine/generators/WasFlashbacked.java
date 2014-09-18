package org.rnd.jmagic.engine.generators;

import org.rnd.jmagic.engine.*;

/**
 * Evaluates to non-empty if any of the given objects had their flashback costs
 * paid; empty if none of them did.
 */
public class WasFlashbacked extends SetGenerator
{
	private SetGenerator what;

	private WasFlashbacked(SetGenerator what)
	{
		this.what = what;
	}

	public static WasFlashbacked instance(SetGenerator what)
	{
		return new WasFlashbacked(what);
	}

	@Override
	public Set evaluate(GameState state, Identified thisObject)
	{
		for(GameObject object: this.what.evaluate(state, thisObject).getAll(GameObject.class))
		{
			for(CostCollection alt: object.getAlternateCost())
				if(alt != null && alt.type.equals(org.rnd.jmagic.abilities.keywords.Flashback.COST_TYPE))
					return NonEmpty.set;
		}

		return Empty.set;
	}

}
