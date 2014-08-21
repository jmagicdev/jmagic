package org.rnd.jmagic.engine.eventTypes;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

public final class PlayerChooseNumber extends EventType
{
	public static final EventType INSTANCE = new PlayerChooseNumber();

	private PlayerChooseNumber()
	{
		super("PLAYER_CHOOSE_NUMBER");
	}

	@Override
	public Parameter affects()
	{
		return Parameter.PLAYER;
	}

	@Override
	public boolean perform(Game game, Event event, java.util.Map<Parameter, Set> parameters)
	{
		Player player = parameters.get(Parameter.PLAYER).getOne(Player.class);
		org.rnd.util.NumberRange range = getRange(parameters.get(Parameter.CHOICE));
		String description = parameters.get(Parameter.TYPE).getOne(String.class);

		int chosen = player.chooseNumber(range, description);
		event.setResult(Identity.instance(chosen));
		return true;
	}
}