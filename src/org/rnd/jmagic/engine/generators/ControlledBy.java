package org.rnd.jmagic.engine.generators;

import org.rnd.jmagic.engine.*;

/**
 * Evaluates to the objects controlled by the given players either on the
 * battlefield, or any other zones if specified
 */
public class ControlledBy extends SetGenerator
{
	public static ControlledBy instance(SetGenerator controller)
	{
		return new ControlledBy(controller, Battlefield.instance());
	}

	public static ControlledBy instance(SetGenerator controller, SetGenerator zone)
	{
		return new ControlledBy(controller, zone);
	}

	private final SetGenerator controller;
	private final SetGenerator zone;

	private ControlledBy(SetGenerator controllerGenerator, SetGenerator zoneGenerator)
	{
		this.controller = controllerGenerator;
		this.zone = zoneGenerator;
	}

	@Override
	public Set evaluate(GameState state, Identified thisObject)
	{
		Set ret = new Set();
		java.util.Set<Zone> zones = this.zone.evaluate(state, thisObject).getAll(Zone.class);
		java.util.Set<Integer> controllers = new java.util.HashSet<Integer>();
		for(Player player: this.controller.evaluate(state, thisObject).getAll(Player.class))
			controllers.add(player.ID);

		for(Zone zone: zones)
		{
			for(GameObject object: zone.objects)
				if(controllers.contains(object.controllerID))
					ret.add(object);
			if(state.stack().equals(zone))
				for(int controllerID: controllers)
					ret.addAll(state.waitingTriggers.get(controllerID));
		}
		return ret;
	}
}
