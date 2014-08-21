package org.rnd.jmagic.engine.eventTypes;

import org.rnd.jmagic.engine.*;

public final class PutOntoBattlefieldAttachedToChoice extends EventType
{
	public static final EventType INSTANCE = new PutOntoBattlefieldAttachedToChoice();

	private PutOntoBattlefieldAttachedToChoice()
	{
		super("PUT_ONTO_BATTLEFIELD_ATTACHED_TO_CHOICE");
	}

	@Override
	public Parameter affects()
	{
		return Parameter.OBJECT;
	}

	@Override
	public boolean attempt(Game game, Event event, java.util.Map<Parameter, Set> parameters)
	{
		Set cause = parameters.get(Parameter.CAUSE);
		GameObject object = parameters.get(Parameter.OBJECT).getOne(GameObject.class);
		Set controller = parameters.get(Parameter.CONTROLLER);

		for(GameObject choice: parameters.get(Parameter.CHOICE).getAll(GameObject.class))
		{
			java.util.Map<Parameter, Set> putOntoBattlefieldParameters = new java.util.HashMap<Parameter, Set>();
			putOntoBattlefieldParameters.put(Parameter.CAUSE, cause);
			putOntoBattlefieldParameters.put(Parameter.OBJECT, new Set(object));
			putOntoBattlefieldParameters.put(Parameter.CONTROLLER, controller);
			putOntoBattlefieldParameters.put(Parameter.TARGET, new Set(choice));
			Event putOntoBattlefield = createEvent(game, "Put " + object + " onto the battlefield attached to " + choice, PUT_ONTO_BATTLEFIELD_ATTACHED_TO, putOntoBattlefieldParameters);
			if(putOntoBattlefield.attempt(event))
				return true;
		}
		return false;
	}

	@Override
	public boolean perform(Game game, Event event, java.util.Map<Parameter, Set> parameters)
	{
		Set cause = parameters.get(Parameter.CAUSE);
		GameObject object = parameters.get(Parameter.OBJECT).getOne(GameObject.class);
		Set controller = parameters.get(Parameter.CONTROLLER);
		Set chooser = controller;
		if(parameters.containsKey(Parameter.PLAYER))
			chooser = parameters.get(Parameter.PLAYER);
		Set choices = parameters.get(Parameter.CHOICE);

		java.util.List<?> chosen = chooser.getOne(Player.class).sanitizeAndChoose(game.actualState, 1, choices, PlayerInterface.ChoiceType.OBJECTS, PlayerInterface.ChooseReason.ATTACH);

		java.util.Map<Parameter, Set> putOntoBattlefieldParameters = new java.util.HashMap<Parameter, Set>();
		putOntoBattlefieldParameters.put(Parameter.CAUSE, cause);
		putOntoBattlefieldParameters.put(Parameter.OBJECT, new Set(object));
		putOntoBattlefieldParameters.put(Parameter.CONTROLLER, controller);
		putOntoBattlefieldParameters.put(Parameter.TARGET, Set.fromCollection(chosen));
		Event putOntoBattlefield = createEvent(game, "Put " + object + " onto the battlefield attached to " + chosen, PUT_ONTO_BATTLEFIELD_ATTACHED_TO, putOntoBattlefieldParameters);
		boolean status = putOntoBattlefield.perform(event, false);

		event.setResult(putOntoBattlefield.getResultGenerator());
		return status;
	}
}