package org.rnd.jmagic.engine.eventTypes;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

public final class TransformOnePermanent extends EventType
{	public static final EventType INSTANCE = new TransformOnePermanent();

	 private TransformOnePermanent()
	{
		super("TRANSFORM_ONE_PERMANENT");
	}

	@Override
	public Parameter affects()
	{
		return Parameter.OBJECT;
	}

	@Override
	public boolean perform(Game game, Event event, java.util.Map<Parameter, Set> parameters)
	{
		GameObject object = parameters.get(Parameter.OBJECT).getOne(GameObject.class);
		GameObject physical = object.getPhysical();
		if(null != physical.getBackFace())
			physical.setTransformed(!physical.isTransformed());
		event.setResult(Empty.set);
		return true;
	}
}