package org.rnd.jmagic.engine.eventTypes;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

public final class Scry extends EventType
{	public static final EventType INSTANCE = new Scry();

	 private Scry()
	{
		super("SCRY");
	}

	@Override
	public Parameter affects()
	{
		return Parameter.PLAYER;
	}

	@Override
	public boolean perform(Game game, Event event, java.util.Map<Parameter, Set> parameters)
	{
		int num = Sum.get(parameters.get(Parameter.NUMBER));
		if(num < 0)
			num = 0;

		Player player = parameters.get(Parameter.PLAYER).getOne(Player.class);
		Zone library = player.getLibrary(game.actualState);

		Set topCards = TopCards.instance(num, Identity.instance(library)).evaluate(game, null);

		java.util.Map<Parameter, Set> lookParameters = new java.util.HashMap<Parameter, Set>();
		lookParameters.put(Parameter.CAUSE, parameters.get(Parameter.CAUSE));
		lookParameters.put(Parameter.OBJECT, topCards);
		lookParameters.put(Parameter.PLAYER, new Set(player));
		Event lookEvent = createEvent(game, "Look at the top " + num + " cards of your library.", EventType.LOOK, lookParameters);
		lookEvent.perform(event, true);

		player = player.getActual();
		library = library.getActual();

		java.util.Map<Parameter, Set> moveParameters = new java.util.HashMap<Parameter, Set>();
		moveParameters.put(Parameter.CAUSE, parameters.get(Parameter.CAUSE));
		moveParameters.put(Parameter.NUMBER, new Set(new org.rnd.util.NumberRange(0, num)));
		moveParameters.put(Parameter.FROM, new Set(library));
		moveParameters.put(Parameter.TO, new Set(library));
		moveParameters.put(Parameter.OBJECT, topCards);
		moveParameters.put(Parameter.CHOICE, new Set(PlayerInterface.ChooseReason.SCRY_TO_BOTTOM));
		moveParameters.put(Parameter.INDEX, new Set(-1));
		moveParameters.put(Parameter.PLAYER, new Set(player));
		Event moveEvent = createEvent(game, "Put any number of them on the bottom of your library.", EventType.MOVE_CHOICE, moveParameters);
		moveEvent.perform(event, true);

		player = player.getActual();
		library = library.getActual();

		java.util.Map<Parameter, Set> reorderParameters = new java.util.HashMap<Parameter, Set>();
		reorderParameters.put(Parameter.CAUSE, parameters.get(Parameter.CAUSE));
		reorderParameters.put(Parameter.OBJECT, topCards);
		reorderParameters.put(Parameter.TO, new Set(library));
		reorderParameters.put(Parameter.INDEX, new Set(1));
		reorderParameters.put(Parameter.PLAYER, new Set(player));
		Event reorderEvent = createEvent(game, "Put the rest on top of your library in any order.", EventType.MOVE_OBJECTS, reorderParameters);
		reorderEvent.perform(event, true);

		event.setResult(Empty.set);

		return true;
	}
}