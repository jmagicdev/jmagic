package org.rnd.jmagic.engine.eventTypes;

import org.rnd.jmagic.engine.*;

public final class RemoveCountersChoice extends EventType
{	public static final EventType INSTANCE = new RemoveCountersChoice();

	 private RemoveCountersChoice()
	{
		super("REMOVE_COUNTERS_CHOICE");
	}

	@Override
	public Parameter affects()
	{
		return Parameter.OBJECT;
	}

	@Override
	public boolean attempt(Game game, Event event, java.util.Map<Parameter, Set> parameters)
	{
		java.util.Set<Counter> counters = parameters.get(Parameter.COUNTER).getAll(Counter.class);

		org.rnd.util.NumberRange number = null;
		if(parameters.containsKey(Parameter.NUMBER))
			number = getRange(parameters.get(Parameter.NUMBER));
		else
			number = new org.rnd.util.NumberRange(1, 1);

		if(counters.size() < number.getLower(0))
			return false;
		return true;
	}

	@Override
	public void makeChoices(Game game, Event event, java.util.Map<Parameter, Set> parameters)
	{
		Player player = parameters.get(Parameter.PLAYER).getOne(Player.class);
		java.util.Set<Counter> counters = parameters.get(Parameter.COUNTER).getAll(Counter.class);
		org.rnd.util.NumberRange number = null;
		if(parameters.containsKey(Parameter.NUMBER))
			number = getRange(parameters.get(Parameter.NUMBER));
		else
			number = new org.rnd.util.NumberRange(1, 1);

		java.util.List<Counter> choice = new java.util.LinkedList<Counter>();
		if(counters.size() <= number.getLower(0))
		{
			choice.addAll(counters);
			if(counters.size() < number.getLower(0))
				event.allChoicesMade = false;
		}
		else
		{
			choice.addAll(player.choose(new PlayerInterface.ChooseParameters<Counter>(new Set(number), new java.util.LinkedList<Counter>(counters), PlayerInterface.ChoiceType.STRING, PlayerInterface.ChooseReason.CHOOSE_COUNTERS)));
			event.allChoicesMade = true;
			event.putChoices(player, choice);
		}
	}

	@Override
	public boolean perform(Game game, Event event, java.util.Map<Parameter, Set> parameters)
	{
		Player player = parameters.get(Parameter.PLAYER).getOne(Player.class);
		Set choice = event.getChoices(player);

		boolean allRemoved = true;

		Set object = parameters.get(Parameter.OBJECT);
		Set result = new Set();
		for(Counter counter: choice.getAll(Counter.class))
		{
			java.util.Map<Parameter, Set> counterParameters = new java.util.HashMap<Parameter, Set>();
			counterParameters.put(Parameter.CAUSE, parameters.get(Parameter.CAUSE));
			counterParameters.put(Parameter.COUNTER, new Set(counter.getType()));
			counterParameters.put(Parameter.OBJECT, new Set(game.actualState.get(counter.sourceID)));
			Event removeCounter = createEvent(game, "Remove a " + counter + " from " + object + ".", EventType.REMOVE_ONE_COUNTER, counterParameters);
			boolean status = removeCounter.perform(event, false);
			if(!status)
				allRemoved = false;
			result.addAll(removeCounter.getResult());
		}
		event.setResult(result);

		return allRemoved;
	}
}