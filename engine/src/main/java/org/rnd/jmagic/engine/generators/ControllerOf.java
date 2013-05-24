package org.rnd.jmagic.engine.generators;

import org.rnd.jmagic.engine.*;

/**
 * Evaluates to the controller of each given Controllable, and the controller
 * specified for each given ZoneChange
 */
public class ControllerOf extends SetGenerator
{
	public static ControllerOf instance(SetGenerator what)
	{
		return new ControllerOf(what);
	}

	private final SetGenerator objects;

	private ControllerOf(SetGenerator objects)
	{
		this.objects = objects;
	}

	@Override
	public Set evaluate(GameState state, Identified thisObject)
	{
		Set ret = new Set();
		Set objects = this.objects.evaluate(state, thisObject);
		for(Controllable o: objects.getAll(Controllable.class))
			ret.add(state.<Player>get(o.getController(state).ID));
		for(ZoneChange zc: objects.getAll(ZoneChange.class))
			ret.add(state.<Player>get(zc.controllerID));
		return ret;
	}
}
