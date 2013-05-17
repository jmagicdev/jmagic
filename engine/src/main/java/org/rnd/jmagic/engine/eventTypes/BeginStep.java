package org.rnd.jmagic.engine.eventTypes;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

public final class BeginStep extends EventType
{	public static final EventType INSTANCE = new BeginStep();

	 private BeginStep()
	{
		super("BEGIN_STEP");
	}

	@Override
	public Parameter affects()
	{
		return Parameter.STEP;
	}

	@Override
	public boolean perform(Game game, Event event, java.util.Map<Parameter, Set> parameters)
	{
		Step step = parameters.get(Parameter.STEP).getOne(Step.class);
		game.physicalState.setCurrentStep(step);
		event.setResult(Identity.instance(step));
		return true;
	}
}