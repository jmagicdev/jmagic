package org.rnd.jmagic.engine.generators;

import org.rnd.jmagic.engine.*;

/**
 * Evaluates to non-empty if any of the given objects had their bestow costs
 * paid, or if their past selves had their bestow costs paid.
 */
public class WasBestowed extends SetGenerator
{
	private SetGenerator what;

	private WasBestowed(SetGenerator what)
	{
		this.what = what;
	}

	public static WasBestowed instance(SetGenerator what)
	{
		return new WasBestowed(what);
	}

	@Override
	public Set evaluate(GameState state, Identified thisObject)
	{
		for(GameObject object: this.what.evaluate(state, thisObject).getAll(GameObject.class))
		{
			for(CostCollection alt: object.getAlternateCost())
				if(alt != null && alt.type.equals(org.rnd.jmagic.abilities.keywords.Bestow.BESTOW_COST))
					return NonEmpty.set;

			if(object.pastSelf == -1)
				continue;

			for(CostCollection alt: state.<GameObject>get(object.pastSelf).getAlternateCost())
				if(alt != null && alt.type.equals(org.rnd.jmagic.abilities.keywords.Bestow.BESTOW_COST))
					return NonEmpty.set;
		}

		return Empty.set;
	}

}
