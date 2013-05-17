package org.rnd.jmagic.engine.eventTypes;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

public final class ExileChoice extends EventType
{	public static final EventType INSTANCE = new ExileChoice();

	 private ExileChoice()
	{
		super("EXILE_CHOICE");
	}

	@Override
	public Parameter affects()
	{
		return Parameter.OBJECT;
	}

	@Override
	public boolean attempt(Game game, Event event, java.util.Map<Parameter, Set> parameters)
	{
		int required = getRange(parameters.get(Parameter.NUMBER)).getLower(0);

		java.util.Set<GameObject> chosen = new java.util.HashSet<GameObject>();
		for(GameObject object: parameters.get(Parameter.OBJECT).getAll(GameObject.class))
		{
			java.util.Map<Parameter, Set> newParameters = new java.util.HashMap<Parameter, Set>();
			newParameters.put(Parameter.CAUSE, parameters.get(Parameter.CAUSE));
			newParameters.put(Parameter.TO, new Set(game.actualState.exileZone()));
			newParameters.put(Parameter.OBJECT, new Set(object));
			if(createEvent(game, "Exile " + object + ".", MOVE_OBJECTS, newParameters).attempt(event))
			{
				chosen.add(object);
				if(chosen.size() >= required)
					return true;
			}
		}

		return false;
	}

	@Override
	public void makeChoices(Game game, Event event, java.util.Map<Parameter, Set> parameters)
	{
		Player player = parameters.get(Parameter.PLAYER).getOne(Player.class);
		org.rnd.util.NumberRange number = getRange(parameters.get(Parameter.NUMBER));

		// offer the choices to the player
		java.util.Set<GameObject> choices = parameters.get(Parameter.OBJECT).getAll(GameObject.class);
		java.util.Collection<GameObject> chosen = player.sanitizeAndChoose(game.actualState, number.getLower(0), number.getUpper(choices.size()), choices, PlayerInterface.ChoiceType.OBJECTS, PlayerInterface.ChooseReason.EXILE);
		if(number.contains(chosen.size()))
			event.allChoicesMade = true;

		event.putChoices(player, chosen);
	}

	@Override
	public boolean perform(Game game, Event event, java.util.Map<Parameter, Set> parameters)
	{
		Set chosen = event.getChoices(parameters.get(Parameter.PLAYER).getOne(Player.class));

		java.util.Map<Parameter, Set> moveParameters = new java.util.HashMap<Parameter, Set>();
		moveParameters.put(Parameter.CAUSE, parameters.get(Parameter.CAUSE));
		moveParameters.put(Parameter.TO, new Set(game.actualState.exileZone()));
		moveParameters.put(Parameter.OBJECT, chosen);
		if(parameters.containsKey(Parameter.HIDDEN))
			moveParameters.put(Parameter.HIDDEN, Empty.set);

		Event exileEvent = createEvent(game, "Exile " + chosen + ".", MOVE_OBJECTS, moveParameters);
		boolean status = exileEvent.perform(event, false);

		event.setResult(exileEvent.getResultGenerator());

		return event.allChoicesMade && status;
	}
}