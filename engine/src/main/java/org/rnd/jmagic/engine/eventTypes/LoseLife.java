package org.rnd.jmagic.engine.eventTypes;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

public final class LoseLife extends EventType
{	public static final EventType INSTANCE = new LoseLife();

	 private LoseLife()
	{
		super("LOSE_LIFE");
	}

	@Override
	public Parameter affects()
	{
		return Parameter.PLAYER;
	}

	@Override
	public boolean attempt(Game game, Event event, java.util.Map<Parameter, Set> parameters)
	{
		int lifeloss = Sum.get(parameters.get(Parameter.NUMBER));
		if(lifeloss <= 0)
			return true;

		Set players = parameters.get(Parameter.PLAYER);
		for(Player player: players.getAll(Player.class))
		{
			java.util.HashMap<Parameter, Set> newParameters = new java.util.HashMap<Parameter, Set>(parameters);
			newParameters.put(Parameter.PLAYER, new Set(player));
			Event loseLifeOnePlayer = createEvent(game, player + " loses " + lifeloss + " life", LOSE_LIFE_ONE_PLAYER, newParameters);
			if(!loseLifeOnePlayer.attempt(event))
				return false;
		}

		return true;
	}

	@Override
	public boolean perform(Game game, Event event, java.util.Map<Parameter, Set> parameters)
	{
		int lifeloss = Sum.get(parameters.get(Parameter.NUMBER));
		if(lifeloss <= 0)
		{
			event.setResult(Empty.set);
			return true;
		}

		Set players = parameters.get(Parameter.PLAYER);
		Set result = new Set();
		for(Player player: players.getAll(Player.class))
		{
			java.util.HashMap<Parameter, Set> newParameters = new java.util.HashMap<Parameter, Set>(parameters);
			newParameters.put(Parameter.PLAYER, new Set(player));
			Event loseLifeOnePlayer = createEvent(game, player + " loses " + lifeloss + " life", LOSE_LIFE_ONE_PLAYER, newParameters);
			loseLifeOnePlayer.perform(event, false);
			result.addAll(loseLifeOnePlayer.getResult());
		}

		event.setResult(result);
		return true;
	}
}