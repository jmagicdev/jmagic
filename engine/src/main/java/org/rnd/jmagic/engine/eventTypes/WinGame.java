package org.rnd.jmagic.engine.eventTypes;

import org.rnd.jmagic.engine.*;

public final class WinGame extends EventType
{	public static final EventType INSTANCE = new WinGame();

	 private WinGame()
	{
		super("WIN_GAME");
	}

	@Override
	public Parameter affects()
	{
		return Parameter.PLAYER;
	}

	@Override
	public boolean perform(Game game, Event event, java.util.Map<Parameter, Set> parameters)
	{
		org.rnd.jmagic.sanitized.SanitizedEvent sanitized = new org.rnd.jmagic.sanitized.SanitizedEvent(event);
		for(Player player: game.actualState.players)
			player.alert(sanitized);

		parameters.get(Parameter.PLAYER).getOne(Player.class).wonGame = true;
		throw new Game.GameOverException();
	}
}