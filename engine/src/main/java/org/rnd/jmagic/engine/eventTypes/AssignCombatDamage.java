package org.rnd.jmagic.engine.eventTypes;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

public final class AssignCombatDamage extends EventType
{
	public static final EventType INSTANCE = new AssignCombatDamage();

	private AssignCombatDamage()
	{
		super("ASSIGN_COMBAT_DAMAGE");
	}

	@Override
	public Parameter affects()
	{
		return Parameter.OBJECT;
	}

	@SuppressWarnings("unchecked")
	@Override
	public boolean perform(Game game, Event event, java.util.Map<Parameter, Set> parameters)
	{
		event.setResult(Empty.set);

		GameObject assigning = parameters.get(Parameter.OBJECT).getOne(GameObject.class);
		int amount = assigning.getPower();
		if(parameters.containsKey(Parameter.NUMBER))
			amount = Sum.get(parameters.get(Parameter.NUMBER));
		else if(game.actualState.assignCombatDamageUsingToughness)
			amount = assigning.getToughness();
		amount = Math.max(amount, 0);

		java.util.List<Target> assignTo = parameters.get(Parameter.TARGET).getOne(java.util.List.class);

		if(amount == 0)
			for(Target t: assignTo)
				t.division = 0;
		else
			assigning.getController(game.actualState).divide(amount, 0, assigning.ID, "damage", assignTo);

		event.setResult(Empty.set);
		return true;
	}
}