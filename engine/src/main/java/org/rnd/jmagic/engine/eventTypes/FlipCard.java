package org.rnd.jmagic.engine.eventTypes;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

public final class FlipCard extends EventType
{	public static final EventType INSTANCE = new FlipCard();

	 private FlipCard()
	{
		super("FLIP_CARD");
	}

	@Override
	public Parameter affects()
	{
		return Parameter.OBJECT;
	}

	@Override
	public boolean perform(Game game, Event event, java.util.Map<Parameter, Set> parameters)
	{
		for(GameObject object: parameters.get(Parameter.OBJECT).getAll(GameObject.class))
			object.getPhysical().setFlipped(true);

		event.setResult(Empty.set);
		return true;
	}
}