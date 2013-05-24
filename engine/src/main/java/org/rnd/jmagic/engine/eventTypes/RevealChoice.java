package org.rnd.jmagic.engine.eventTypes;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

public final class RevealChoice extends EventType
{	public static final EventType INSTANCE = new RevealChoice();

	 private RevealChoice()
	{
		super("REVEAL_CHOICE");
	}

	@Override
	public Parameter affects()
	{
		return Parameter.OBJECT;
	}

	@Override
	public boolean attempt(Game game, Event event, java.util.Map<Parameter, Set> parameters)
	{
		int number = 1;
		if(parameters.containsKey(Parameter.NUMBER))
			number = Sum.get(parameters.get(Parameter.NUMBER));
		java.util.Set<GameObject> objects = parameters.get(Parameter.OBJECT).getAll(GameObject.class);
		return (objects.size() >= number);
	}

	@Override
	public void makeChoices(Game game, Event event, java.util.Map<Parameter, Set> parameters)
	{
		java.util.Set<GameObject> objects = parameters.get(Parameter.OBJECT).getAll(GameObject.class);
		org.rnd.util.NumberRange number;
		if(parameters.containsKey(Parameter.NUMBER))
			number = getRange(parameters.get(Parameter.NUMBER));
		else
			number = new org.rnd.util.NumberRange(1, 1);

		Player chooser = parameters.get(Parameter.PLAYER).getOne(Player.class);
		java.util.List<GameObject> chosen = chooser.sanitizeAndChoose(game.actualState, number.getLower(0), number.getUpper(objects.size()), objects, PlayerInterface.ChoiceType.OBJECTS, PlayerInterface.ChooseReason.CHOOSE_CARD_TO_REVEAL);
		if(!number.contains(chosen.size()))
			event.allChoicesMade = false;
		event.putChoices(chooser, chosen);
	}

	@Override
	public boolean perform(Game game, Event event, java.util.Map<Parameter, Set> parameters)
	{
		Set cause = parameters.get(Parameter.CAUSE);
		Set choices = event.getChoices(parameters.get(Parameter.PLAYER).getOne(Player.class));

		java.util.Map<Parameter, Set> revealParameters = new java.util.HashMap<Parameter, Set>();
		revealParameters.put(Parameter.CAUSE, cause);
		revealParameters.put(Parameter.OBJECT, choices);
		if(parameters.containsKey(Parameter.EFFECT))
			revealParameters.put(Parameter.EFFECT, parameters.get(Parameter.EFFECT));
		Event revealEvent = createEvent(game, "Reveal " + choices, EventType.REVEAL, revealParameters);

		boolean ret = revealEvent.perform(event, false);

		event.setResult(revealEvent.getResultGenerator());

		return ret;
	}
}