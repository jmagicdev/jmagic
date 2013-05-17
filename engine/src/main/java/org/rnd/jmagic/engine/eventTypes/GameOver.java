package org.rnd.jmagic.engine.eventTypes;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

public final class GameOver extends EventType
{	public static final EventType INSTANCE = new GameOver();

	 private GameOver()
	{
		super("GAME_OVER");
	}

	@Override
	public Parameter affects()
	{
		return null;
	}

	@Override
	public boolean perform(Game game, Event event, java.util.Map<Parameter, Set> parameters)
	{
		org.rnd.jmagic.sanitized.SanitizedEvent sanitized = new org.rnd.jmagic.sanitized.SanitizedEvent(event);
		for(Player player: game.actualState.players)
			player.alert(sanitized);

		event.setResult(Empty.set);
		return true;
	}
}