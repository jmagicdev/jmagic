package org.rnd.jmagic.engine.eventTypes;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

public final class GainLife extends EventType
{
	public static final EventType INSTANCE = new GainLife();

	private GainLife()
	{
		super("GAIN_LIFE");
	}

	@Override
	public Parameter affects()
	{
		return Parameter.PLAYER;
	}

	@Override
	public boolean attempt(Game game, Event event, java.util.Map<Parameter, Set> parameters)
	{
		int lifeGain = Sum.get(parameters.get(Parameter.NUMBER));
		if(lifeGain <= 0)
			return true;

		Set players = parameters.get(Parameter.PLAYER);
		for(Player player: players.getAll(Player.class))
		{
			java.util.HashMap<Parameter, Set> newParameters = new java.util.HashMap<Parameter, Set>(parameters);
			newParameters.put(Parameter.PLAYER, new Set(player));
			Event gainLifeOnePlayer = createEvent(game, player + " loses " + lifeGain + " life", GAIN_LIFE_ONE_PLAYER, newParameters);
			if(!gainLifeOnePlayer.attempt(event))
				return false;
		}

		return true;
	}

	@Override
	public boolean perform(Game game, Event event, java.util.Map<Parameter, Set> parameters)
	{
		int lifeGain = Sum.get(parameters.get(Parameter.NUMBER));
		if(lifeGain <= 0)
		{
			event.setResult(Empty.set);
			return true;
		}

		Set result = new Set();
		Set players = parameters.get(Parameter.PLAYER);
		for(Player player: players.getAll(Player.class))
		{
			java.util.HashMap<Parameter, Set> newParameters = new java.util.HashMap<Parameter, Set>(parameters);
			newParameters.put(Parameter.PLAYER, new Set(player));
			Event gain = createEvent(game, player + " gains " + lifeGain + " life", GAIN_LIFE_ONE_PLAYER, newParameters);
			gain.perform(event, false);
			result.addAll(gain.getResult());
		}

		event.setResult(result);
		return true;
	}
}