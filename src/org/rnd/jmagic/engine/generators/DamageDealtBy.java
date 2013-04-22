package org.rnd.jmagic.engine.generators;

import org.rnd.jmagic.engine.*;

/**
 * Evaluates to all damage assignments from the given set that were dealt by one
 * of the given sources
 */
public class DamageDealtBy extends SetGenerator
{
	public static DamageDealtBy instance(SetGenerator object, SetGenerator damage)
	{
		return new DamageDealtBy(object, damage);
	}

	private final SetGenerator sources;
	private final SetGenerator damage;

	private DamageDealtBy(SetGenerator object, SetGenerator damage)
	{
		this.sources = object;
		this.damage = damage;
	}

	@Override
	public Set evaluate(GameState state, Identified thisObject)
	{
		Set ret = new Set();
		Set sources = this.sources.evaluate(state, thisObject);
		Set damage = this.damage.evaluate(state, thisObject);

		for(DamageAssignment assignment: damage.getAll(DamageAssignment.class))
			if(sources.contains(state.get(assignment.sourceID)))
				ret.add(assignment);

		return ret;
	}
}
