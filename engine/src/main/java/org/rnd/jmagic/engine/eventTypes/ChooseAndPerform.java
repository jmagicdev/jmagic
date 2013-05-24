package org.rnd.jmagic.engine.eventTypes;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

public final class ChooseAndPerform extends EventType
{	public static final EventType INSTANCE = new ChooseAndPerform();

	 private ChooseAndPerform()
	{
		super("CHOOSE_AND_PERFORM");
	}

	@Override
	public Parameter affects()
	{
		return Parameter.EVENT;
	}

	@Override
	public boolean attempt(Game game, Event event, java.util.Map<Parameter, Set> parameters)
	{
		for(EventFactory factory: parameters.get(Parameter.EVENT).getAll(EventFactory.class))
		{
			Event choice = factory.createEvent(game, event.getSource());
			if(choice.attempt(event))
				return true;
		}

		return false;
	}

	@Override
	public boolean perform(Game game, Event event, java.util.Map<Parameter, Set> parameters)
	{
		java.util.Collection<Event> validChoices = new java.util.LinkedList<Event>();

		for(EventFactory factory: parameters.get(Parameter.EVENT).getAll(EventFactory.class))
		{
			Event choice = factory.createEvent(game, event.getSource());
			if(choice.attempt(event))
				validChoices.add(choice);
		}

		Player player = parameters.get(Parameter.PLAYER).getOne(Player.class);
		java.util.List<Event> choice = player.getPhysical().sanitizeAndChoose(game.physicalState, 1, validChoices, PlayerInterface.ChoiceType.EVENT, PlayerInterface.ChooseReason.CHOOSE_EVENT);

		if(choice.size() == 0)
		{
			event.setResult(Empty.set);
			return false;
		}

		Event chosenEvent = choice.get(0);
		boolean status = chosenEvent.perform(event, false);
		event.setResult(Identity.instance(chosenEvent));
		return status;
	}
}