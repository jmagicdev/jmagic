package org.rnd.jmagic.abilities;

import org.rnd.jmagic.engine.*;

public final class PreventCombatDamageDealtToOrBy extends DamageReplacementEffect
{
	private SetGenerator who;

	public PreventCombatDamageDealtToOrBy(Game game, SetGenerator who, String whoName)
	{
		super(game, "Prevent all combat damage that would be dealt to and dealt by " + whoName);
		this.who = who;
		this.makePreventionEffect();
	}

	@Override
	public DamageAssignment.Batch match(Event context, DamageAssignment.Batch damageAssignments)
	{
		Set who = this.who.evaluate(this.game, this.getSourceObject(this.game.actualState));

		DamageAssignment.Batch ret = new DamageAssignment.Batch();
		for(DamageAssignment assignment: damageAssignments)
			if(assignment.isCombatDamage && (who.contains(context.state.get(assignment.sourceID)) || who.contains(context.state.get(assignment.takerID))))
				ret.add(assignment);
		return ret;
	}

	@Override
	public java.util.List<EventFactory> prevent(DamageAssignment.Batch damageAssignments)
	{
		damageAssignments.clear();
		return new java.util.LinkedList<EventFactory>();
	}
}