package org.rnd.jmagic.engine.eventTypes;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

public final class PutOntoBattlefieldChoiceAttachedTo extends EventType
{	public static final EventType INSTANCE = new PutOntoBattlefieldChoiceAttachedTo();

	 private PutOntoBattlefieldChoiceAttachedTo()
	{
		super("PUT_ONTO_BATTLEFIELD_CHOICE_ATTACHED_TO");
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
		int number = 1;
		if(parameters.containsKey(Parameter.NUMBER))
			number = Sum.get(parameters.get(Parameter.NUMBER));
		Set target = parameters.get(Parameter.TARGET);

		int successes = 0;
		for(GameObject object: objects.getAll(GameObject.class))
		{
			java.util.Map<Parameter, Set> putOntoBattlefieldParameters = new java.util.HashMap<Parameter, Set>();
			putOntoBattlefieldParameters.put(Parameter.CAUSE, cause);
			putOntoBattlefieldParameters.put(Parameter.OBJECT, new Set(object));
			putOntoBattlefieldParameters.put(Parameter.CONTROLLER, controller);
			putOntoBattlefieldParameters.put(Parameter.TARGET, target);
			Event putOntoBattlefield = createEvent(game, "Put " + object + " onto the battlefield attached to " + target, PUT_ONTO_BATTLEFIELD_ATTACHED_TO, putOntoBattlefieldParameters);
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
	public boolean perform(Game game, Event event, java.util.Map<Parameter, Set> parameters)
	{
		Set cause = parameters.get(Parameter.CAUSE);
		Player controller = parameters.get(Parameter.CONTROLLER).getOne(Player.class);
		Set objects = parameters.get(Parameter.OBJECT);
		int number = 1;
		if(parameters.containsKey(Parameter.NUMBER))
			number = Sum.get(parameters.get(Parameter.NUMBER));
		Player chooser = controller;
		if(parameters.containsKey(Parameter.PLAYER))
			chooser = parameters.get(Parameter.PLAYER).getOne(Player.class);

		// offer the choices to the player
		java.util.List<GameObject> choices = chooser.sanitizeAndChoose(game.actualState, number, objects.getAll(GameObject.class), PlayerInterface.ChoiceType.OBJECTS, PlayerInterface.ChooseReason.PUT_ONTO_BATTLEFIELD);
		if(choices.size() == 0)
			return false;

		boolean allChosen = true;
		if(choices.size() != number)
			allChosen = false;

		Set stuffToPutOntoBattlefield = Set.fromCollection(choices);
		Set target = parameters.get(Parameter.TARGET);

		java.util.Map<Parameter, Set> putOntoBattlefieldParameters = new java.util.HashMap<Parameter, Set>();
		putOntoBattlefieldParameters.put(Parameter.CAUSE, cause);
		putOntoBattlefieldParameters.put(Parameter.CONTROLLER, new Set(controller));
		putOntoBattlefieldParameters.put(Parameter.OBJECT, stuffToPutOntoBattlefield);
		putOntoBattlefieldParameters.put(Parameter.TARGET, target);
		Event putOntoBattlefield = createEvent(game, "Put " + stuffToPutOntoBattlefield + " onto the battlefield attached to " + target, PUT_ONTO_BATTLEFIELD_ATTACHED_TO, putOntoBattlefieldParameters);

		boolean moveSuccess = putOntoBattlefield.perform(event, false);
		event.setResult(putOntoBattlefield.getResultGenerator());
		return allChosen && moveSuccess;
	}
}