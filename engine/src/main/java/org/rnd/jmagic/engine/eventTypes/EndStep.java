package org.rnd.jmagic.engine.eventTypes;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

public final class EndStep extends EventType
{
	public static final EventType INSTANCE = new EndStep();

	private EndStep()
	{
		super("END_STEP");
	}

	@Override
	public Parameter affects()
	{
		return Parameter.CAUSE;
	}

	@Override
	public boolean perform(Game game, Event event, java.util.Map<Parameter, Set> parameters)
	{
		game.physicalState.setPreviousStep(parameters.get(Parameter.STEP).getOne(Step.class));
		createEvent(game, "Empty all mana pools.", EventType.EMPTY_ALL_MANA_POOLS, null).perform(event, false);
		event.setResult(Empty.set);
		return true;
	}
}