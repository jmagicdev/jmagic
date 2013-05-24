package org.rnd.jmagic.engine.eventTypes;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

public final class DiscardRandom extends EventType
{	public static final EventType INSTANCE = new DiscardRandom();

	 private DiscardRandom()
	{
		super("DISCARD_RANDOM");
	}

	@Override
	public Parameter affects()
	{
		return Parameter.CARD;
	}

	@Override
	public boolean attempt(Game game, Event event, java.util.Map<Parameter, Set> parameters)
	{
		int numDiscard = Sum.get(parameters.get(Parameter.NUMBER));

		if(parameters.containsKey(Parameter.CARD))
		{
			// if a set of cards has specifically been given, make sure its
			// big enough to support all the discards
			int numChoices = parameters.get(Parameter.CARD).size();
			int numPlayers = parameters.get(Parameter.PLAYER).size();

			return (numChoices >= (numPlayers * numDiscard));
		}

		for(Player player: parameters.get(Parameter.PLAYER).getAll(Player.class))
		{
			if(player.getHand(game.actualState).objects.size() < numDiscard)
				return false;
		}
		return true;
	}

	@Override
	public boolean perform(Game game, Event event, java.util.Map<Parameter, Set> parameters)
	{
		// get the number of cards out of the parameter
		int numberOfCards = Sum.get(parameters.get(Parameter.NUMBER));

		Set cause = parameters.get(Parameter.CAUSE);

		boolean allDiscarded = true;
		Set result = new Set();

		java.util.List<GameObject> cardsInHand = null;
		if(parameters.containsKey(Parameter.CARD))
		{
			cardsInHand = new java.util.LinkedList<GameObject>();
			for(GameObject card: parameters.get(Parameter.CARD).getAll(GameObject.class))
				cardsInHand.add(card);
		}

		// get the cards in the player's hand
		for(Player player: parameters.get(Parameter.PLAYER).getAll(Player.class))
		{
			player = player.getActual();

			// build the list of cards to choose from
			if(!parameters.containsKey(Parameter.CARD))
			{
				cardsInHand = new java.util.LinkedList<GameObject>();
				for(GameObject card: player.getHand(game.actualState))
					cardsInHand.add(card);
			}

			// choose n cards randomly
			java.util.Collections.shuffle(cardsInHand);
			Set discardThese = new Set();
			while(discardThese.size() < numberOfCards && 0 < cardsInHand.size())
				discardThese.add(cardsInHand.remove(0));
			if(discardThese.size() < numberOfCards)
				allDiscarded = false;

			// perform the discard event
			java.util.Map<Parameter, Set> discardParameters = new java.util.HashMap<Parameter, Set>();
			discardParameters.put(Parameter.CAUSE, cause);
			discardParameters.put(Parameter.CARD, discardThese);
			Event discard = createEvent(game, player + " discards " + discardThese + ".", DISCARD_CARDS, discardParameters);
			if(!discard.perform(event, false))
				allDiscarded = false;
			result.addAll(discard.getResult());
		}

		event.setResult(Identity.instance(result));
		return allDiscarded;
	}
}