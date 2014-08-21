package org.rnd.jmagic.engine.eventTypes;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

public final class ShuffleIntoLibrary extends EventType
{
	public static final EventType INSTANCE = new ShuffleIntoLibrary();

	private ShuffleIntoLibrary()
	{
		super("SHUFFLE_INTO_LIBRARY");
	}

	@Override
	public Parameter affects()
	{
		return Parameter.OBJECT;
	}

	@Override
	public boolean attempt(Game game, Event event, java.util.Map<Parameter, Set> parameters)
	{
		for(GameObject object: parameters.get(Parameter.OBJECT).getAll(GameObject.class))
			if(object.isGhost())
				return false;
		return true;
	}

	@Override
	public boolean perform(Game game, Event event, java.util.Map<Parameter, Set> parameters)
	{
		Set cause = parameters.get(Parameter.CAUSE);
		java.util.Set<Player> player = parameters.get(Parameter.OBJECT).getAll(Player.class);

		java.util.Map<Parameter, Set> moveParameters = new java.util.HashMap<Parameter, Set>();
		java.util.Set<GameObject> cards = parameters.get(Parameter.OBJECT).getAll(GameObject.class);
		moveParameters.put(Parameter.CAUSE, cause);
		moveParameters.put(Parameter.INDEX, new Set(-1));
		// if this is a stacked game, the cards will stay on the bottom and
		// be player-ordered
		if(!game.noRandom)
			moveParameters.put(Parameter.RANDOM, Empty.set);
		moveParameters.put(Parameter.OBJECT, Set.fromCollection(cards));
		Event move = createEvent(game, "Put " + cards + " into their owners' libraries.", PUT_INTO_LIBRARY, moveParameters);
		boolean moveStatus = move.perform(event, true);

		// 701.15d If an effect would cause a player to shuffle one or more
		// specific objects into a library, and a replacement or prevention
		// effect causes all such objects to be moved to another zone
		// instead, that library isn't shuffled.
		out: while(true)
		{
			// 701.15e If an effect would cause a player to shuffle a set of
			// objects into a library, that library is shuffled even if
			// there are no objects in that set.
			if(cards.isEmpty())
				break out;

			// keys are cards, values are the libraries those cards should
			// go to
			java.util.Map<Integer, Integer> expectedDestinations = new java.util.HashMap<Integer, Integer>();
			for(GameObject card: cards)
				expectedDestinations.put(card.ID, card.getOwner(game.actualState).getLibrary(game.actualState).ID);

			for(ZoneChange zc: move.getResult().getAll(ZoneChange.class))
				if(expectedDestinations.get(zc.oldObjectID) == zc.destinationZoneID)
					break out;
			event.setResult(new Set());
			return false;
		}

		java.util.Map<Parameter, Set> shuffleParameters = new java.util.HashMap<Parameter, Set>();
		shuffleParameters.put(Parameter.CAUSE, cause);
		shuffleParameters.put(Parameter.PLAYER, Set.fromCollection(player));
		Event shuffle = createEvent(game, player + " shuffles their library.", SHUFFLE_LIBRARY, shuffleParameters);
		boolean shuffleStatus = shuffle.perform(event, false);

		Set result = new Set();
		result.addAll(move.getResult());
		result.addAll(shuffle.getResult());
		event.setResult(result);
		return moveStatus && shuffleStatus;
	}
}