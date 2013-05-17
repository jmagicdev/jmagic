package org.rnd.jmagic.engine.eventTypes;

import org.rnd.jmagic.engine.*;

public final class PutCounterOnChoice extends EventType
{	public static final EventType INSTANCE = new PutCounterOnChoice();

	 private PutCounterOnChoice()
	{
		super("PUT_COUNTER_ON_CHOICE");
	}

	@Override
	public Parameter affects()
	{
		return Parameter.CHOICE;
	}

	@Override
	public void makeChoices(Game game, Event event, java.util.Map<Parameter, Set> parameters)
	{
		int numberOfCards = 1;

		java.util.Set<GameObject> objects = parameters.get(Parameter.CHOICE).getAll(GameObject.class);

		for(Player player: parameters.get(Parameter.PLAYER).getAll(Player.class))
		{
			java.util.Collection<GameObject> choices = player.sanitizeAndChoose(game.actualState, numberOfCards, objects, PlayerInterface.ChoiceType.OBJECTS, PlayerInterface.ChooseReason.PUT_COUNTER);
			if(choices.size() != numberOfCards)
				event.allChoicesMade = false;
			event.putChoices(player, choices);
		}
	}

	@Override
	public boolean perform(Game game, Event event, java.util.Map<Parameter, Set> parameters)
	{
		boolean allReceivedCounters = event.allChoicesMade;
		Set cause = parameters.get(Parameter.CAUSE);
		Set counter = parameters.get(Parameter.COUNTER);
		Set one = new Set(1);
		Set result = new Set();

		for(Player player: parameters.get(Parameter.PLAYER).getAll(Player.class))
		{
			Set putOnThese = event.getChoices(player);

			java.util.Map<Parameter, Set> putCountersParameters = new java.util.HashMap<Parameter, Set>();
			putCountersParameters.put(Parameter.CAUSE, cause);
			putCountersParameters.put(Parameter.COUNTER, counter);
			putCountersParameters.put(Parameter.NUMBER, one);
			putCountersParameters.put(Parameter.OBJECT, putOnThese);
			Event putCounters = createEvent(game, player + " puts a " + counter + " counter on " + putOnThese + ".", PUT_COUNTERS, putCountersParameters);
			if(!putCounters.perform(event, false))
				allReceivedCounters = false;
			result.addAll(putCounters.getResult());
		}

		event.setResult(result);
		return allReceivedCounters;
	}
}