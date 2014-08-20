package org.rnd.jmagic.engine.eventTypes;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

public final class Wish extends EventType
{	public static final EventType INSTANCE = new Wish();

	 private Wish()
	{
		super("WISH");
	}

	@Override
	public Parameter affects()
	{
		return null;
	}

	@Override
	public boolean attempt(Game game, Event event, java.util.Map<Parameter, Set> parameters)
	{
		return !parameters.get(Parameter.CHOICE).getAll(GameObject.class).isEmpty();
	}

	@Override
	public boolean perform(Game game, Event event, java.util.Map<Parameter, Set> parameters)
	{
		event.setResult(Empty.set);

		Set cause = parameters.get(Parameter.CAUSE);
		Player player = parameters.get(Parameter.PLAYER).getOne(Player.class);
		Set choices = Set.fromCollection(player.sanitizeAndChoose(game.actualState, 0, 1, parameters.get(Parameter.CHOICE).getAll(GameObject.class), PlayerInterface.ChoiceType.OBJECTS, new PlayerInterface.ChooseReason(PlayerInterface.ChooseReason.GAME, event.getName(), false)));

		java.util.Map<EventType.Parameter, Set> revealParameters = new java.util.HashMap<EventType.Parameter, Set>();
		revealParameters.put(Parameter.CAUSE, cause);
		revealParameters.put(Parameter.OBJECT, choices);
		createEvent(game, "Reveal " + choices, EventType.REVEAL, revealParameters).perform(event, false);

		java.util.Map<EventType.Parameter, Set> moveParameters = new java.util.HashMap<EventType.Parameter, Set>();
		moveParameters.put(Parameter.CAUSE, cause);
		moveParameters.put(Parameter.TO, new Set(player.getHand(game.actualState)));
		moveParameters.put(Parameter.OBJECT, choices);
		createEvent(game, "Put " + choices + " into your hand", EventType.MOVE_OBJECTS, moveParameters).perform(event, false);

		return true;
	}
}