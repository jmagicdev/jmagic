package org.rnd.jmagic.engine.eventTypes;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

public final class RestartTheGame extends EventType
{
	public static final EventType INSTANCE = new RestartTheGame();

	private RestartTheGame()
	{
		super("RESTART_THE_GAME");
	}

	@Override
	public Parameter affects()
	{
		return null;
	}

	@Override
	public boolean perform(Game game, Event event, java.util.Map<Parameter, Set> parameters)
	{
		org.rnd.jmagic.sanitized.SanitizedEvent sanitized = new org.rnd.jmagic.sanitized.SanitizedEvent(event, "Restart the game");
		for(Player player: game.actualState.players)
			player.alert(sanitized);

		GameObject oldCause = event.getSource();
		PlayerInterface firstPlayerComm = oldCause.getController(game.actualState).comm;
		GameState oldPhysical = game.physicalState;
		oldPhysical.currentPhase().steps.clear();
		oldPhysical.currentTurn().phases.clear();

		game.physicalState = new GameState(game);
		game.started = false;

		// Maps zone IDs in the old physical state to zone IDs in the new
		// physical state; objects that are exempt from the restart
		// procedure need to "stay" in their zone, so put the new copies of
		// those objects into the same zone they used to be in. Player IDs
		// are also represented here, in order to facilitate updating the
		// controller/owner of the ability that is currently resolving.
		java.util.Map<Integer, Integer> idMap = new java.util.HashMap<Integer, Integer>();
		idMap.put(-1, -1);
		idMap.put(oldPhysical.battlefield().ID, game.physicalState.battlefield().ID);
		idMap.put(oldPhysical.commandZone().ID, game.physicalState.commandZone().ID);
		idMap.put(oldPhysical.exileZone().ID, game.physicalState.exileZone().ID);
		idMap.put(oldPhysical.stack().ID, game.physicalState.stack().ID);

		Player firstPlayer = null;
		java.util.Map<PlayerInterface, Integer> playerMap = new java.util.HashMap<PlayerInterface, Integer>();
		for(Player p: oldPhysical.players)
		{
			Player newPlayer = game.addInterface(p.comm);
			playerMap.put(p.comm, newPlayer.ID);
			idMap.put(p.getGraveyardID(), newPlayer.getGraveyardID());
			idMap.put(p.getHandID(), newPlayer.getHandID());
			idMap.put(p.getLibraryID(), newPlayer.getLibraryID());
			idMap.put(p.getSideboardID(), newPlayer.getSideboardID());
			idMap.put(p.ID, newPlayer.ID);

			if(p.comm == firstPlayerComm)
				firstPlayer = newPlayer;
		}

		Set exemptFromRestart = parameters.get(Parameter.OBJECT);

		// 713.2. All _Magic_ cards involved in the game that was restarted
		// when it ended, including phased-out permanents and nontraditional
		// _Magic_ cards, are involved in the new game, even if those cards
		// were not originally involved in the restarted game. Ownership of
		// cards in the new game doesn't change, regardless of their
		// location when the new game begins.
		// Example: A player casts Living Wish, bringing a creature card
		// into the game from outside the game. Then that game is restarted.
		// The creature card will be part of that player's library when the
		// new game begins.
		// The above rule basically says we should leave the sideboard where
		// it is, but don't bother tracking where cards that left the
		// sideboard ended up since they should be shuffled into their
		// owner's library
		for(Player p: game.actualState.players)
			exemptFromRestart.addAll(p.getSideboard(game.actualState).objects);

		for(GameObject o: oldPhysical.getAllObjects())
		{
			if(o.isCard())
			{
				Player oldOwner = o.getOwner(oldPhysical);
				GameObject newObject = o.create(game);

				Player newOwner = game.physicalState.get(playerMap.get(oldOwner.comm));
				newObject.setOwner(newOwner);
				if(exemptFromRestart.contains(o))
					game.physicalState.<Zone>get(idMap.get(o.zoneID)).addToTop(newObject);
				else
					newOwner.getLibrary(game.physicalState).addToTop(newObject);
			}
		}

		// This event and its source need to exist in the new state so it
		// can finish resolving
		game.physicalState.put(event.clone(game.physicalState));
		GameObject newCause = oldCause.clone(game.physicalState);

		// Update some vital ID-based properties so generators in the new
		// state work properly
		newCause.controllerID = idMap.get(oldCause.controllerID);
		newCause.ownerID = idMap.get(oldCause.ownerID);

		game.startGame(firstPlayer);
		game.restarted = true;

		oldPhysical.clear();
		event.setResult(Identity.instance(game));
		return true;
	}
}