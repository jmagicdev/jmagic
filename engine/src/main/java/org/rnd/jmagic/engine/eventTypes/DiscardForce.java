package org.rnd.jmagic.engine.eventTypes;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

public final class DiscardForce extends EventType
{	public static final EventType INSTANCE = new DiscardForce();

	 private DiscardForce()
	{
		super("DISCARD_FORCE");
	}

	@Override
	public Parameter affects()
	{
		return Parameter.TARGET;
	}

	@Override
	public boolean attempt(Game game, Event event, java.util.Map<Parameter, Set> parameters)
	{
		int successes = 0;
		Set cause = parameters.get(Parameter.CAUSE);
		int required = 1;
		if(parameters.containsKey(Parameter.NUMBER))
			required = Sum.get(parameters.get(Parameter.NUMBER));

		Player player = parameters.get(Parameter.TARGET).getOne(Player.class);
		Set cards = null;
		if(parameters.containsKey(Parameter.CHOICE))
			cards = parameters.get(Parameter.CHOICE);
		else
			cards = new Set(player.getHand(game.actualState).objects);

		successes = 0;
		while(!cards.isEmpty())
		{
			GameObject thisCard = cards.getOne(GameObject.class);
			cards.remove(thisCard);
			java.util.Map<Parameter, Set> newParameters = new java.util.HashMap<Parameter, Set>();
			parameters.put(Parameter.CAUSE, cause);
			parameters.put(Parameter.CARD, new Set(thisCard));
			if(createEvent(game, thisCard.getOwner(game.actualState) + " discards " + thisCard + ".", DISCARD_ONE_CARD, newParameters).attempt(event))
				successes++;

			if(successes == required)
				return true;
		}
		return false;
	}

	@Override
	public boolean perform(Game game, Event event, java.util.Map<Parameter, Set> parameters)
	{
		// get the number of cards out of the parameters
		int numberOfCards = 1;
		if(parameters.containsKey(Parameter.NUMBER))
			numberOfCards = Sum.get(parameters.get(Parameter.NUMBER));

		if(numberOfCards < 0)
			numberOfCards = 0;

		boolean allDiscarded = true;
		Set cause = parameters.get(Parameter.CAUSE);

		Player target = parameters.get(Parameter.TARGET).getOne(Player.class);

		// offer the choices to the player
		// TODO : "Search" is a keyword action. This should not be a search.
		java.util.Map<Parameter, Set> chooseParameters = new java.util.HashMap<Parameter, Set>();
		chooseParameters.put(Parameter.CAUSE, cause);
		chooseParameters.put(Parameter.PLAYER, parameters.get(Parameter.PLAYER));
		chooseParameters.put(Parameter.NUMBER, new Set(numberOfCards));
		chooseParameters.put(Parameter.CARD, new Set(target.getHand(game.actualState)));
		if(parameters.containsKey(Parameter.CHOICE))
			chooseParameters.put(Parameter.TYPE, parameters.get(Parameter.CHOICE));
		Event chooseEvent = createEvent(game, "Choose cards to discard", EventType.SEARCH, chooseParameters);
		chooseEvent.perform(event, false);

		java.util.Set<GameObject> choices = chooseEvent.getResult().getAll(GameObject.class);
		if(choices.size() != numberOfCards)
			allDiscarded = false;
		if(choices.size() != 0)
		{
			// build the Set of objects to discard
			Set discardThese = new Set(choices);

			// perform the discard event
			java.util.Map<Parameter, Set> discardParameters = new java.util.HashMap<Parameter, Set>();
			discardParameters.put(Parameter.CAUSE, cause);
			discardParameters.put(Parameter.CARD, discardThese);
			Event discard = createEvent(game, target + " discards " + discardThese + ".", DISCARD_CARDS, discardParameters);
			if(!discard.perform(event, false))
				allDiscarded = false;
			event.setResult(discard.getResultGenerator());
			return allDiscarded;
		}

		event.setResult(Empty.set);
		return allDiscarded;
	}
}