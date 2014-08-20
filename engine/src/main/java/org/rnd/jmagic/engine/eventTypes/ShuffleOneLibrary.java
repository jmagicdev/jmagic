package org.rnd.jmagic.engine.eventTypes;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

public final class ShuffleOneLibrary extends EventType
{	public static final EventType INSTANCE = new ShuffleOneLibrary();

	 private ShuffleOneLibrary()
	{
		super("SHUFFLE_ONE_LIBRARY");
	}

	@Override
	public Parameter affects()
	{
		return Parameter.PLAYER;
	}

	@Override
	public boolean perform(Game game, Event event, java.util.Map<Parameter, Set> parameters)
	{
		Set result = new Set();

		Player player = parameters.get(Parameter.PLAYER).getOne(Player.class);
		Zone actualLibrary = player.getLibrary(game.actualState);
		event.addShuffle(actualLibrary.ID);
		if(!game.noRandom)
			event.removeIndexedZone(actualLibrary);

		result.add(actualLibrary);
		event.setResult(Identity.fromCollection(result));
		return true;
	}
}