package org.rnd.jmagic.engine.eventTypes;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

public final class PutIntoHand extends EventType
{
	public static final EventType INSTANCE = new PutIntoHand();

	private PutIntoHand()
	{
		super("PUT_INTO_HAND");
	}

	@Override
	public Parameter affects()
	{
		return Parameter.PERMANENT;
	}

	@Override
	public boolean perform(Game game, Event event, java.util.Map<Parameter, Set> parameters)
	{
		Set permanents = parameters.get(Parameter.PERMANENT);
		java.util.Map<Player, Set> whoOwnsWhat = this.whoOwnsWhat(game.actualState, permanents);

		Set result = new Set();
		boolean allBounced = true;
		Set cause = parameters.get(Parameter.CAUSE);
		for(java.util.Map.Entry<Player, Set> ownedThings: whoOwnsWhat.entrySet())
		{
			Player owner = ownedThings.getKey();
			Set thesePermanents = ownedThings.getValue();

			java.util.Map<Parameter, Set> moveParameters = new java.util.HashMap<Parameter, Set>();
			moveParameters.put(Parameter.CAUSE, cause);
			moveParameters.put(Parameter.TO, new Set(owner.getHand(game.actualState)));
			moveParameters.put(Parameter.OBJECT, thesePermanents);
			Event move = createEvent(game, "Put " + thesePermanents + " into " + owner + "'s hand.", MOVE_OBJECTS, moveParameters);

			if(!move.perform(event, false))
				allBounced = false;
			result.addAll(move.getResult());
		}

		event.setResult(Identity.fromCollection(result));

		return allBounced;
	}

	private java.util.Map<Player, Set> whoOwnsWhat(GameState state, Set permanents)
	{
		java.util.Map<Player, Set> whoOwnsWhat = new java.util.HashMap<Player, Set>();
		for(GameObject object: permanents.getAll(GameObject.class))
		{
			Player owner = object.getOwner(state);
			if(!whoOwnsWhat.containsKey(owner))
				whoOwnsWhat.put(owner, new Set());
			whoOwnsWhat.get(owner).add(object);
		}
		return whoOwnsWhat;
	}
}