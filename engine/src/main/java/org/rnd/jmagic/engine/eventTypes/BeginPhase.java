package org.rnd.jmagic.engine.eventTypes;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

public final class BeginPhase extends EventType
{	public static final EventType INSTANCE = new BeginPhase();

	 private BeginPhase()
	{
		super("BEGIN_PHASE");
	}

	@Override
	public Parameter affects()
	{
		return Parameter.PHASE;
	}

	@Override
	public boolean perform(Game game, Event event, java.util.Map<Parameter, Set> parameters)
	{
		Phase phase = parameters.get(Parameter.PHASE).getOne(Phase.class);
		game.physicalState.setCurrentPhase(phase);
		event.setResult(Identity.instance(phase));
		return true;
	}
}