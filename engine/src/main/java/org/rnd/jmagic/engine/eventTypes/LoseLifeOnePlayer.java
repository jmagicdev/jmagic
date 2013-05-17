package org.rnd.jmagic.engine.eventTypes;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

public final class LoseLifeOnePlayer extends EventType
{	public static final EventType INSTANCE = new LoseLifeOnePlayer();

	 private LoseLifeOnePlayer()
	{
		super("LOSE_LIFE_ONE_PLAYER");
	}

	@Override
	public Parameter affects()
	{
		return Parameter.PLAYER;
	}

	@Override
	public boolean perform(Game game, Event event, java.util.Map<Parameter, Set> parameters)
	{
		int lifeLoss = Sum.get(parameters.get(Parameter.NUMBER));

		if(lifeLoss < 0)
			lifeLoss = 0;

		Player player = parameters.get(Parameter.PLAYER).getOne(Player.class);
		Player physicalPlayer = player.getPhysical();
		physicalPlayer.lifeTotal -= lifeLoss;
		if(parameters.containsKey(Parameter.DAMAGE))
			if(null != player.minimumLifeTotalFromDamage)
				if(physicalPlayer.lifeTotal < player.minimumLifeTotalFromDamage)
					physicalPlayer.lifeTotal = player.minimumLifeTotalFromDamage;

		event.setResult(Identity.instance(player, lifeLoss));
		return true;
	}
}