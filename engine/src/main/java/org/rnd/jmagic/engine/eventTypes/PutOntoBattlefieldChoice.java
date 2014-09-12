package org.rnd.jmagic.engine.eventTypes;

import org.rnd.jmagic.engine.*;

public final class PutOntoBattlefieldChoice extends EventType
{
	public static final EventType INSTANCE = new PutOntoBattlefieldChoice();

	private PutOntoBattlefieldChoice()
	{
		super("PUT_ONTO_BATTLEFIELD_CHOICE");
	}

	@Override
	public Parameter affects()
	{
		return Parameter.CONTROLLER;
	}

	@Override
	public boolean attempt(Game game, Event event, java.util.Map<Parameter, Set> parameters)
	{
		Set cause = parameters.get(Parameter.CAUSE);
		Set controller = parameters.get(Parameter.CONTROLLER);
		Set objects = parameters.get(Parameter.OBJECT);
		int number = getRange(parameters.get(Parameter.NUMBER)).getLower(0);
		EventType type = EventType.PUT_ONTO_BATTLEFIELD;
		if(parameters.containsKey(Parameter.EFFECT))
			type = parameters.get(Parameter.EFFECT).getOne(EventType.class);

		int successes = 0;
		for(GameObject object: objects.getAll(GameObject.class))
		{
			java.util.Map<Parameter, Set> putOntoBattlefieldParameters = new java.util.HashMap<Parameter, Set>();
			putOntoBattlefieldParameters.put(Parameter.CAUSE, cause);
			putOntoBattlefieldParameters.put(Parameter.OBJECT, new Set(object));
			putOntoBattlefieldParameters.put(Parameter.CONTROLLER, controller);
			Event putOntoBattlefield = createEvent(game, "Put " + object + " onto the battlefield", type, putOntoBattlefieldParameters);
			if(putOntoBattlefield.attempt(event))
			{
				successes++;
				if(successes == number)
					return true;
			}
		}

		return false;
	}

	@Override
	public void makeChoices(Game game, Event event, java.util.Map<Parameter, Set> parameters)
	{
		java.util.Set<GameObject> choices = parameters.get(Parameter.OBJECT).getAll(GameObject.class);
		Player chooser;
		if(parameters.containsKey(Parameter.PLAYER))
			chooser = parameters.get(Parameter.PLAYER).getOne(Player.class);
		else
			chooser = parameters.get(Parameter.CONTROLLER).getOne(Player.class);

		org.rnd.util.NumberRange number = getRange(parameters.get(Parameter.NUMBER));
		int lower = number.getLower(0);
		int upper = number.getUpper(choices.size());
		String choiceName;
		if(lower == upper)
		{
			if(1 == lower)
				choiceName = "Put a card onto the battlefield.";
			else
				choiceName = "Put " + org.rnd.util.NumberNames.get(lower) + " cards onto the battlefield.";
		}
		else if(lower == 0)
		{
			if(upper == 1)
				choiceName = "You may put a card onto the battlefield.";
			else
				choiceName = "Put up to " + org.rnd.util.NumberNames.get(upper) + " cards onto the battlefield.";
		}
		else
			choiceName = "Put between " + org.rnd.util.NumberNames.get(lower) + " and " + org.rnd.util.NumberNames.get(upper) + " cards onto the battlefield.";

		// offer the choices to the player
		PlayerInterface.ChooseReason reason = new PlayerInterface.ChooseReason(PlayerInterface.ChooseReason.GAME, choiceName, true);
		java.util.Collection<GameObject> chosen = chooser.sanitizeAndChoose(game.actualState, lower, upper, choices, PlayerInterface.ChoiceType.OBJECTS, reason);
		if(number.contains(chosen.size()))
			event.allChoicesMade = true;

		event.putChoices(chooser, chosen);
	}

	@Override
	public boolean perform(Game game, Event event, java.util.Map<Parameter, Set> parameters)
	{
		Set cause = parameters.get(Parameter.CAUSE);
		Player controller = parameters.get(Parameter.CONTROLLER).getOne(Player.class);

		Player chooser;
		if(parameters.containsKey(Parameter.PLAYER))
			chooser = parameters.get(Parameter.PLAYER).getOne(Player.class);
		else
			chooser = controller;

		EventType type = EventType.PUT_ONTO_BATTLEFIELD;
		if(parameters.containsKey(Parameter.EFFECT))
			type = parameters.get(Parameter.EFFECT).getOne(EventType.class);

		Set stuffToPutOntoBattlefield = event.getChoices(chooser);

		java.util.Map<Parameter, Set> putOntoBattlefieldParameters = new java.util.HashMap<Parameter, Set>();
		putOntoBattlefieldParameters.put(Parameter.CAUSE, cause);
		putOntoBattlefieldParameters.put(Parameter.CONTROLLER, new Set(controller));
		putOntoBattlefieldParameters.put(Parameter.OBJECT, stuffToPutOntoBattlefield);
		Event putOntoBattlefield = createEvent(game, "Put " + stuffToPutOntoBattlefield + " onto the battlefield", type, putOntoBattlefieldParameters);

		boolean moveSuccess = putOntoBattlefield.perform(event, false);
		event.setResult(putOntoBattlefield.getResultGenerator());
		return event.allChoicesMade && moveSuccess;
	}

}