package org.rnd.jmagic.abilities;

import org.rnd.jmagic.engine.*;

public class PreventCombatDamage extends DamageReplacementEffect
{
	private SetGenerator fromWho;

	public PreventCombatDamage(Game game)
	{
		super(game, "Prevent all combat damage");
		this.makePreventionEffect();
		this.fromWho = null;
	}

	public PreventCombatDamage(Game game, SetGenerator fromWho, String name)
	{
		super(game, name);
		this.makePreventionEffect();
		this.fromWho = fromWho;
	}

	@Override
	public DamageAssignment.Batch match(Event context, DamageAssignment.Batch damageAssignments)
	{
		DamageAssignment.Batch ret = new DamageAssignment.Batch();
		Set fromWho = null;
		if(this.fromWho != null)
			fromWho = this.fromWho.evaluate(context.state, this.getSourceObject(context.state));
		for(DamageAssignment assignment: damageAssignments)
		{
			if(!assignment.isCombatDamage)
				continue;
			if(fromWho == null || fromWho.contains(context.state.get(assignment.sourceID)))
				ret.add(assignment);
		}
		return ret;
	}

	@Override
	public java.util.List<EventFactory> prevent(DamageAssignment.Batch damageAssignments)
	{
		damageAssignments.clear();
		return new java.util.LinkedList<EventFactory>();
	}
}
