package org.rnd.jmagic.engine.eventTypes;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

public final class LoseGame extends EventType
{	public static final EventType INSTANCE = new LoseGame();

	 private LoseGame()
	{
		super("LOSE_GAME");
	}

	@Override
	public Parameter affects()
	{
		return Parameter.PLAYER;
	}

	@Override
	public boolean perform(Game game, Event event, java.util.Map<Parameter, Set> parameters)
	{
		Game.LoseReason reason = parameters.get(Parameter.CAUSE).getOne(Game.LoseReason.class);
		if(reason == null)
			throw new RuntimeException("LOSE_GAME called without a Game.LoseReason");

		Set players = parameters.get(Parameter.PLAYER);

		org.rnd.jmagic.sanitized.SanitizedEvent sanitized = new org.rnd.jmagic.sanitized.SanitizedEvent(event, players.toString() + " lost the game");
		for(Player player: game.actualState.players)
			player.alert(sanitized);

		// 104.5. If a player loses the game, he or she leaves the game.
		game.physicalState.removePlayers(players.getAll(Player.class));

		event.setResult(Identity.fromCollection(players));
		return true;
	}
}