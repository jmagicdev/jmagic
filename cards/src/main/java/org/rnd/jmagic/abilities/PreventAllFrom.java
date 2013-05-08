package org.rnd.jmagic.abilities;

import org.rnd.jmagic.engine.*;

public final class PreventAllFrom extends DamageReplacementEffect
{
	private final SetGenerator from;

	public PreventAllFrom(Game game, SetGenerator from, String fromDescription)
	{
		super(game, "Prevent all damage " + fromDescription + " would deal.");
		this.from = from;
		this.makePreventionEffect();
	}

	@Override
	public DamageAssignment.Batch match(Event context, DamageAssignment.Batch damageAssignments)
	{
		DamageAssignment.Batch ret = new DamageAssignment.Batch();
		Set from = this.from.evaluate(context.game, this.getSourceObject(context.state));
		for(DamageAssignment damage: damageAssignments)
			if(from.contains(context.state.get(damage.sourceID)))
				ret.add(damage);
		return ret;
	}

	@Override
	public java.util.List<EventFactory> prevent(DamageAssignment.Batch damageAssignments)
	{
		damageAssignments.clear();
		return java.util.Collections.emptyList();
	}
}