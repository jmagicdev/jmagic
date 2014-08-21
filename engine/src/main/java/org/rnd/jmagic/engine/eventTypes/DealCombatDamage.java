package org.rnd.jmagic.engine.eventTypes;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

public final class DealCombatDamage extends EventType
{
	public static final EventType INSTANCE = new DealCombatDamage();

	private DealCombatDamage()
	{
		super("DEAL_COMBAT_DAMAGE");
	}

	@Override
	public Parameter affects()
	{
		return Parameter.TARGET;
	}

	@Override
	public boolean perform(Game game, Event event, java.util.Map<Parameter, Set> parameters)
	{
		event.addDamage(parameters.get(Parameter.TARGET).getAll(DamageAssignment.class));

		event.setResult(Empty.set);
		return true;
	}
}