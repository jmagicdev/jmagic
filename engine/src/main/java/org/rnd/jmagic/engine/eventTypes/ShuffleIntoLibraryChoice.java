package org.rnd.jmagic.engine.eventTypes;

import org.rnd.jmagic.engine.*;

public final class ShuffleIntoLibraryChoice extends EventType
{
	public static final EventType INSTANCE = new ShuffleIntoLibraryChoice();

	private ShuffleIntoLibraryChoice()
	{
		super("SHUFFLE_INTO_LIBRARY_CHOICE");
	}

	@Override
	public Parameter affects()
	{
		return Parameter.PLAYER;
	}

	@Override
	public boolean attempt(Game game, Event event, java.util.Map<Parameter, Set> parameters)
	{
		int required = getRange(parameters.get(Parameter.NUMBER)).getLower(0);
		Set choices = parameters.get(Parameter.CHOICE);
		return choices.size() >= required;
	}

	@Override
	public void makeChoices(Game game, Event event, java.util.Map<Parameter, Set> parameters)
	{
		org.rnd.util.NumberRange number = getRange(parameters.get(Parameter.NUMBER));
		Player choosing = parameters.get(Parameter.PLAYER).getOne(Player.class);
		Set choices = parameters.get(Parameter.CHOICE);

		java.util.List<Object> chosen = choosing.sanitizeAndChoose(game.actualState, number.getLower(0), number.getUpper(choices.size()), choices, PlayerInterface.ChoiceType.OBJECTS, PlayerInterface.ChooseReason.SHUFFLE_OBJECTS);
		event.allChoicesMade = (chosen.size() >= number.getLower(0));
		event.putChoices(choosing, chosen);
	}

	@Override
	public boolean perform(Game game, Event event, java.util.Map<Parameter, Set> parameters)
	{
		Set objectsToShuffle = event.getChoices(parameters.get(Parameter.PLAYER).getOne(Player.class));
		String name = "Shuffle " + objectsToShuffle + " into " + (objectsToShuffle.size() <= 1 ? "its owner's library." : "their owners' libraries.");

		for(GameObject o: objectsToShuffle.getAll(GameObject.class))
			objectsToShuffle.add(o.getOwner(game.actualState));

		boolean ret = event.allChoicesMade;
		java.util.Map<Parameter, Set> shuffleParameters = new java.util.HashMap<Parameter, Set>();
		shuffleParameters.put(Parameter.CAUSE, parameters.get(Parameter.CAUSE));
		shuffleParameters.put(Parameter.OBJECT, objectsToShuffle);
		Event shuffle = createEvent(game, name, SHUFFLE_INTO_LIBRARY, shuffleParameters);
		ret = shuffle.perform(event, false) && ret;

		event.setResult(shuffle.getResultGenerator());
		return ret;
	}
}