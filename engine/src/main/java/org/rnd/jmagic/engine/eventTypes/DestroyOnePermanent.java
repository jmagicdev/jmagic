package org.rnd.jmagic.engine.eventTypes;

import org.rnd.jmagic.engine.*;

public final class DestroyOnePermanent extends EventType
{
	public static final EventType INSTANCE = new DestroyOnePermanent();

	private DestroyOnePermanent()
	{
		super("DESTROY_ONE_PERMANENT");
	}

	@Override
	public Parameter affects()
	{
		return Parameter.PERMANENT;
	}

	@Override
	public boolean perform(Game game, Event event, java.util.Map<Parameter, Set> parameters)
	{
		GameObject permanent = parameters.get(Parameter.PERMANENT).getOne(GameObject.class);
		Player owner = permanent.getOwner(game.actualState);
		Zone graveyard = owner.getGraveyard(game.actualState);

		java.util.Map<Parameter, Set> moveParameters = new java.util.HashMap<Parameter, Set>();
		moveParameters.put(Parameter.CAUSE, parameters.get(Parameter.CAUSE));
		moveParameters.put(Parameter.TO, new Set(graveyard));
		moveParameters.put(Parameter.OBJECT, new Set(permanent));

		// move the permanent to the graveyard
		Event move = createEvent(game, "Put " + permanent + " in " + owner + "'s graveyard.", MOVE_OBJECTS, moveParameters);
		boolean status = move.perform(event, false);

		event.setResult(move.getResultGenerator());
		return status;
	}
}