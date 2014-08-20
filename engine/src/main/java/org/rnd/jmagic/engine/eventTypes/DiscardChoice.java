package org.rnd.jmagic.engine.eventTypes;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

public final class DiscardChoice extends EventType
{	public static final EventType INSTANCE = new DiscardChoice();

	 private DiscardChoice()
	{
		super("DISCARD_CHOICE");
	}

	@Override
	public Parameter affects()
	{
		return Parameter.CHOICE;
	}

	@Override
	public boolean attempt(Game game, Event event, java.util.Map<Parameter, Set> parameters)
	{
		int successes = 0;
		Set cause = parameters.get(Parameter.CAUSE);
		int required = 1;
		if(parameters.containsKey(Parameter.NUMBER))
			required = Sum.get(parameters.get(Parameter.NUMBER));

		for(Player player: parameters.get(Parameter.PLAYER).getAll(Player.class))
		{
			java.util.Set<Card> cards = null;
			if(parameters.containsKey(Parameter.CHOICE))
				cards = parameters.get(Parameter.CHOICE).getAll(Card.class);
			else
				cards = Set.fromCollection(player.getHand(game.actualState).objects).getAll(Card.class);

			successes = 0;
			for(Card thisCard: cards)
			{
				java.util.Map<Parameter, Set> newParameters = new java.util.HashMap<Parameter, Set>();
				newParameters.put(Parameter.CAUSE, cause);
				newParameters.put(Parameter.CARD, new Set(thisCard));
				if(createEvent(game, thisCard.getOwner(game.actualState) + " discards " + thisCard + ".", DISCARD_ONE_CARD, newParameters).attempt(event))
					successes++;

				if(successes == required)
					break;
			}
			if(successes != required)
				return false;
		}
		return true;
	}

	@Override
	public void makeChoices(Game game, Event event, java.util.Map<Parameter, Set> parameters)
	{
		int numberOfCards = 1;
		if(parameters.containsKey(Parameter.NUMBER))
			numberOfCards = Sum.get(parameters.get(Parameter.NUMBER));

		if(numberOfCards < 0)
			numberOfCards = 0;

		java.util.Set<Card> cardsInHand = null;
		boolean specificChoices = parameters.containsKey(Parameter.CHOICE);

		if(specificChoices)
			cardsInHand = parameters.get(Parameter.CHOICE).getAll(Card.class);

		for(Player player: parameters.get(Parameter.PLAYER).getAll(Player.class))
		{
			if(!specificChoices)
				cardsInHand = Set.fromCollection(player.getHand(game.actualState).objects).getAll(Card.class);

			java.util.Collection<Card> choices = player.sanitizeAndChoose(game.actualState, numberOfCards, cardsInHand, PlayerInterface.ChoiceType.OBJECTS, PlayerInterface.ChooseReason.DISCARD);
			if(choices.size() != numberOfCards)
				event.allChoicesMade = false;
			event.putChoices(player, choices);
		}
	}

	@Override
	public boolean perform(Game game, Event event, java.util.Map<Parameter, Set> parameters)
	{
		boolean allDiscarded = event.allChoicesMade;
		Set cause = parameters.get(Parameter.CAUSE);
		Set result = new Set();

		for(Player player: parameters.get(Parameter.PLAYER).getAll(Player.class))
		{
			Set discardThese = event.getChoices(player);

			java.util.Map<Parameter, Set> discardParameters = new java.util.HashMap<Parameter, Set>();
			discardParameters.put(Parameter.CAUSE, cause);
			discardParameters.put(Parameter.CARD, discardThese);
			Event discard = createEvent(game, player + " discards " + discardThese + ".", DISCARD_CARDS, discardParameters);
			if(!discard.perform(event, false))
				allDiscarded = false;
			result.addAll(discard.getResult());
		}

		event.setResult(Identity.fromCollection(result));
		return allDiscarded;
	}
}