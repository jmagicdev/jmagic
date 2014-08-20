package org.rnd.jmagic.engine.eventTypes;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

public final class MulliganSimultaneous extends EventType
{	public static final EventType INSTANCE = new MulliganSimultaneous();

	 private MulliganSimultaneous()
	{
		super("MULLIGAN_SIMULTANEOUS");
	}

	@Override
	public Parameter affects()
	{
		return Parameter.PLAYER;
	}

	@Override
	public boolean perform(Game game, Event event, java.util.Map<Parameter, Set> parameters)
	{
		boolean ret = true;

		java.util.List<Player> orderedPlayers = new java.util.LinkedList<Player>(game.physicalState.players);
		orderedPlayers.retainAll(parameters.get(Parameter.PLAYER).getAll(Player.class));

		java.util.Iterator<Player> iter = orderedPlayers.iterator();
		// keys are player ids, values are event ids (no entry in the map
		// means that the player chose to keep)
		java.util.Map<Integer, Integer> mulligans = new java.util.HashMap<Integer, Integer>();
		while(iter.hasNext())
		{
			Player player = iter.next();
			boolean keep = (player.getHand(game.actualState).objects.size() == 0);

			if(!keep)
			{
				java.util.Map<Parameter, Set> mulliganParameters = new java.util.HashMap<Parameter, Set>();
				mulliganParameters.put(EventType.Parameter.PLAYER, new Set(player));
				Event mulligan = createEvent(game, "Mulligan", EventType.MULLIGAN, mulliganParameters);

				// 103.4a If an effect allows a player to perform an action
				// any time [that player] could mulligan, the player may
				// perform that action at a time he or she would declare
				// whether or not he or she will take a mulligan. ... If the
				// player performs the action, he or she then declares
				// whether or not he or she will take a mulligan.

				// [aka the Serum Powder rule]
				// We loop until the player has made a "real" keep/mulligan
				// decision, since according to 103.4a (see above) a Serum
				// Powder mulligan causes the player to then choose whether
				// to take a mulligan.
				while(!(keep || mulligans.containsKey(player.ID)))
				{
					java.util.Collection<Event> choices = new java.util.HashSet<Event>();
					choices.add(mulligan);
					for(java.util.Map.Entry<Integer, EventFactory> e: player.getActual().mulliganOptions.entrySet())
						choices.add(e.getValue().createEvent(game, game.actualState.<GameObject>get(e.getKey())));

					java.util.List<Event> chosen = player.sanitizeAndChoose(game.physicalState, 0, 1, choices, PlayerInterface.ChoiceType.EVENT, PlayerInterface.ChooseReason.MULLIGAN);

					if(chosen.isEmpty())
						keep = true;
					else
					{
						Event mulliganEvent = chosen.iterator().next();
						if(mulliganEvent.equals(mulligan))
							mulligans.put(player.ID, mulliganEvent.ID);
						else
							mulliganEvent.perform(null, true);
					}
				}
			}

			if(keep)
			{
				iter.remove();
				ret = false;
			}
		}

		// Players that chose to keep are not in the list anymore.
		for(Player player: orderedPlayers)
			game.physicalState.<Event>get(mulligans.get(player.ID)).perform(event, false);

		event.setResult(Identity.fromCollection(orderedPlayers));
		return ret;
	}
}