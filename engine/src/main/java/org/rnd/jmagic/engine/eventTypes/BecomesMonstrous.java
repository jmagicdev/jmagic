package org.rnd.jmagic.engine.eventTypes;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

public final class BecomesMonstrous extends EventType
{
	public static final EventType INSTANCE = new BecomesMonstrous();

	private BecomesMonstrous()
	{
		super("BECOMES_MONSTROUS");
	}

	@Override
	public Parameter affects()
	{
		return Parameter.OBJECT;
	}

	@Override
	public boolean perform(Game game, Event event, java.util.Map<Parameter, Set> parameters)
	{
		event.setResult(Empty.set);
		return true;
	}
}