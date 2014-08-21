package org.rnd.jmagic.engine.eventTypes;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

public final class PlayerChoose extends EventType
{
	public static final EventType INSTANCE = new PlayerChoose();

	private PlayerChoose()
	{
		super("PLAYER_CHOOSE");
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
		Set choices = parameters.get(Parameter.CHOICE);
		org.rnd.util.NumberRange number = getRange(parameters.get(Parameter.NUMBER));
		PlayerInterface.ChoiceType type = parameters.get(Parameter.TYPE).getOne(PlayerInterface.ChoiceType.class);
		PlayerInterface.ChooseReason reason = parameters.get(Parameter.TYPE).getOne(PlayerInterface.ChooseReason.class);

		PlayerInterface.ChooseParameters<java.io.Serializable> chooseParameters;
		chooseParameters = new PlayerInterface.ChooseParameters<java.io.Serializable>(number.getLower(), number.getUpper(), type, reason);
		if(parameters.containsKey(Parameter.OBJECT))
			chooseParameters.thisID = parameters.get(Parameter.OBJECT).getOne(GameObject.class).ID;

		Set result = new Set();
		java.util.List<Object> choice = player.sanitizeAndChoose(game.actualState, choices, chooseParameters);

		if(parameters.containsKey(Parameter.ORDERED))
			result.add(choice);
		else
			result.addAll(choice);

		event.setResult(Identity.fromCollection(result));
		return true;
	}
}