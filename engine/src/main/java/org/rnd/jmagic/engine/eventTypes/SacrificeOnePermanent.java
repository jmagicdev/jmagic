package org.rnd.jmagic.engine.eventTypes;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

public final class SacrificeOnePermanent extends EventType
{
	public static final EventType INSTANCE = new SacrificeOnePermanent();

	private SacrificeOnePermanent()
	{
		super("SACRIFICE_ONE_PERMANENT");
	}

	@Override
	public Parameter affects()
	{
		return Parameter.PERMANENT;
	}

	@Override
	public boolean attempt(Game game, Event event, java.util.Map<Parameter, Set> parameters)
	{
		GameObject permanent = parameters.get(Parameter.PERMANENT).getOne(GameObject.class);
		Player controller = parameters.get(Parameter.PLAYER).getOne(Player.class);
		return permanent.isPermanent() && permanent.getController(game.actualState).equals(controller);
	}

	@Override
	public boolean perform(Game game, Event event, java.util.Map<Parameter, Set> parameters)
	{
		if(!this.attempt(game, event, parameters))
		{
			event.setResult(Empty.set);
			return false;
		}

		GameObject permanent = parameters.get(Parameter.PERMANENT).getOne(GameObject.class);
		Set permanentSet = new Set(permanent);
		Zone graveyard = permanent.getOwner(game.actualState).getGraveyard(game.actualState);

		java.util.Map<Parameter, Set> moveParameters = new java.util.HashMap<Parameter, Set>();
		moveParameters.put(Parameter.CAUSE, parameters.get(Parameter.CAUSE));
		moveParameters.put(Parameter.TO, new Set(graveyard));
		moveParameters.put(Parameter.OBJECT, permanentSet);

		Event move = createEvent(game, "Put " + permanent + " into " + graveyard + ".", MOVE_OBJECTS, moveParameters);
		boolean status = move.perform(event, false);

		event.setResult(move.getResultGenerator());
		return status;
	}
}