package org.rnd.jmagic.engine.eventTypes;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

public final class DeclareOneAttacker extends EventType
{	public static final EventType INSTANCE = new DeclareOneAttacker();

	 private DeclareOneAttacker()
	{
		super("DECLARE_ONE_ATTACKER");
	}

	@Override
	public Parameter affects()
	{
		return null;
	}

	@Override
	public boolean perform(Game game, Event event, java.util.Map<Parameter, Set> parameters)
	{
		event.setResult(Empty.set);
		return true;
	}
}