package org.rnd.jmagic.engine.generators;

import org.rnd.jmagic.engine.*;

/**
 * Evaluates to the source of the given damage assignments
 */
public class SourceOfDamage extends SetGenerator
{
	public static SourceOfDamage instance(SetGenerator what)
	{
		return new SourceOfDamage(what);
	}

	private final SetGenerator damage;

	private SourceOfDamage(SetGenerator damage)
	{
		this.damage = damage;
	}

	@Override
	public Set evaluate(GameState state, Identified thisObject)
	{
		Set ret = new Set();
		for(DamageAssignment assignment: this.damage.evaluate(state, thisObject).getAll(DamageAssignment.class))
			ret.add(state.get(assignment.sourceID));
		return ret;
	}
}
