package org.rnd.jmagic.engine.generators;

import org.rnd.jmagic.engine.*;

/**
 * Evaluates to non-empty if any of the given objects' past selves had their
 * evoke costs paid; empty if none of them did. This generator uses past selves
 * because evoke triggers from the battlefield, but the evoke cost is paid for
 * the spell (on the stack).
 */
public class WasEvoked extends SetGenerator
{
	private SetGenerator what;

	private WasEvoked(SetGenerator what)
	{
		this.what = what;
	}

	public static WasEvoked instance(SetGenerator what)
	{
		return new WasEvoked(what);
	}

	@Override
	public Set evaluate(GameState state, Identified thisObject)
	{
		for(GameObject object: this.what.evaluate(state, thisObject).getAll(GameObject.class))
		{
			CostCollection alt = state.<GameObject>get(object.pastSelf).getAlternateCost();
			if(alt != null && alt.type.equals(org.rnd.jmagic.abilities.keywords.Evoke.EVOKE_COST))
				return NonEmpty.set;
		}

		return Empty.set;
	}

}
