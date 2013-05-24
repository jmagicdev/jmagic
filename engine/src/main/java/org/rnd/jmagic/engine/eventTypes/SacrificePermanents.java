package org.rnd.jmagic.engine.eventTypes;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

public final class SacrificePermanents extends EventType
{	public static final EventType INSTANCE = new SacrificePermanents();

	 private SacrificePermanents()
	{
		super("SACRIFICE_PERMANENTS");
	}

	@Override
	public Parameter affects()
	{
		return Parameter.PERMANENT;
	}

	@Override
	public boolean attempt(Game game, Event event, java.util.Map<Parameter, Set> parameters)
	{
		int battlefield = game.actualState.battlefield().ID;
		int controller = parameters.get(Parameter.PLAYER).getOne(Player.class).ID;

		for(GameObject object: parameters.get(Parameter.PERMANENT).getAll(GameObject.class))
		{
			if(object.isGhost() || object.controllerID != controller)
				return false;

			Zone zone = object.getZone();

			if(zone == null || zone.ID != battlefield)
				return false;
		}
		return true;
	}

	@Override
	public boolean perform(Game game, Event event, java.util.Map<Parameter, Set> parameters)
	{
		Set cause = parameters.get(Parameter.CAUSE);
		Set result = new Set();
		Player player = parameters.get(Parameter.PLAYER).getOne(Player.class);
		int playerID = player.ID;

		boolean allSacrificed = true;
		for(GameObject permanent: parameters.get(Parameter.PERMANENT).getAll(GameObject.class))
		{
			if(permanent.controllerID != playerID)
				continue;

			java.util.Map<Parameter, Set> sacParameters = new java.util.HashMap<Parameter, Set>();
			sacParameters.put(Parameter.CAUSE, cause);
			sacParameters.put(Parameter.PLAYER, new Set(player));
			sacParameters.put(Parameter.PERMANENT, new Set(permanent));
			Event sacrifice = createEvent(game, permanent.getActual().getOwner(game.actualState) + " sacrifices " + permanent + ".", EventType.SACRIFICE_ONE_PERMANENT, sacParameters);
			if(!sacrifice.perform(event, false))
				allSacrificed = false;
			result.addAll(sacrifice.getResult());
		}
		event.setResult(Identity.instance(result));
		return allSacrificed;
	}
}