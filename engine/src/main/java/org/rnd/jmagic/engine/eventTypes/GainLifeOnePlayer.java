package org.rnd.jmagic.engine.eventTypes;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

public final class GainLifeOnePlayer extends EventType
{	public static final EventType INSTANCE = new GainLifeOnePlayer();

	 private GainLifeOnePlayer()
	{
		super("GAIN_LIFE_ONE_PLAYER");
	}

	@Override
	public Parameter affects()
	{
		return Parameter.PLAYER;
	}

	@Override
	public boolean perform(Game game, Event event, java.util.Map<Parameter, Set> parameters)
	{
		int lifeGain = Sum.get(parameters.get(Parameter.NUMBER));

		if(lifeGain < 0)
			lifeGain = 0;

		Player player = parameters.get(Parameter.PLAYER).getOne(Player.class);
		player.getPhysical().lifeTotal += lifeGain;

		event.setResult(Identity.instance(player, lifeGain));
		return true;
	}
}