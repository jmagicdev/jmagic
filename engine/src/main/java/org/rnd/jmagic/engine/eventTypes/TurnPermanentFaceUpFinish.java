package org.rnd.jmagic.engine.eventTypes;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

public final class TurnPermanentFaceUpFinish extends EventType
{
	public static final EventType INSTANCE = new TurnPermanentFaceUpFinish();

	private TurnPermanentFaceUpFinish()
	{
		super("TURN_PERMANENT_FACE_UP_FINISH");
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