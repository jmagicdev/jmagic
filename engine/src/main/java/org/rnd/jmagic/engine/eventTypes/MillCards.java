package org.rnd.jmagic.engine.eventTypes;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

public final class MillCards extends EventType
{	public static final EventType INSTANCE = new MillCards();

	 private MillCards()
	{
		super("MILL_CARDS");
	}

	@Override
	public Parameter affects()
	{
		return Parameter.PLAYER;
	}

	@Override
	public boolean attempt(Game game, Event event, java.util.Map<Parameter, Set> parameters)
	{
		int number = Sum.get(parameters.get(Parameter.NUMBER));

		for(Player player: parameters.get(Parameter.PLAYER).getAll(Player.class))
			if(player.getPhysical().getLibrary(game.physicalState).objects.size() < number)
				return false;

		return true;
	}

	@Override
	public boolean perform(Game game, Event event, java.util.Map<Parameter, Set> parameters)
	{
		boolean allMilled = true;
		Set cause = parameters.get(Parameter.CAUSE);
		int num = Sum.get(parameters.get(Parameter.NUMBER));

		if(num < 0)
			num = 0;

		Set result = new Set();

		for(Player player: parameters.get(Parameter.PLAYER).getAll(Player.class))
		{
			Zone graveyard = player.getGraveyard(game.actualState);
			Zone library = player.getLibrary(game.actualState);
			Set topCards = TopCards.get(num, library);
			java.util.Map<Parameter, Set> moveParameters = new java.util.HashMap<Parameter, Set>();
			moveParameters.put(Parameter.CAUSE, cause);
			moveParameters.put(Parameter.TO, new Set(graveyard));
			moveParameters.put(Parameter.OBJECT, topCards);

			Event move = createEvent(game, "Put " + topCards + " into " + graveyard + ".", MOVE_OBJECTS, moveParameters);
			if(!move.perform(event, false))
				allMilled = false;
			result.addAll(move.getResult());
		}

		event.setResult(Identity.fromCollection(result));
		return allMilled;
	}
}