package org.rnd.jmagic.engine.eventTypes;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

public final class PutIntoHandChoice extends EventType
{
	public static final EventType INSTANCE = new PutIntoHandChoice();

	private PutIntoHandChoice()
	{
		super("PUT_INTO_HAND_CHOICE");
	}

	@Override
	public Parameter affects()
	{
		return Parameter.CHOICE;
	}

	@Override
	public boolean attempt(Game game, Event event, java.util.Map<Parameter, Set> parameters)
	{
		org.rnd.util.NumberRange requiredRange = getRange(parameters.get(Parameter.NUMBER));
		int required = requiredRange.getLower();

		if(required == 0)
			return true;

		int successes = required;
		Set cause = parameters.get(Parameter.CAUSE);
		Set cards = parameters.get(Parameter.CHOICE);

		for(Player player: parameters.get(Parameter.PLAYER).getAll(Player.class))
		{
			// reset this players successes to zero
			successes = 0;
			Set failedCards = new Set();

			while(!cards.isEmpty())
			{
				GameObject thisCard = cards.getOne(GameObject.class);
				cards.remove(thisCard);
				java.util.Map<Parameter, Set> newParameters = new java.util.HashMap<Parameter, Set>();
				parameters.put(Parameter.CAUSE, cause);
				parameters.put(Parameter.CARD, new Set(thisCard));

				// if the player can bounce the card, increment successes.
				// otherwise, give other players the chance to bounce the
				// card.
				if(createEvent(game, player + " returns " + thisCard + " to owners hand.", PUT_INTO_HAND, newParameters).attempt(event))
					successes++;
				else
					failedCards.add(thisCard);

				if(successes == required)
					break;
			}

			// if the player couldn't bounce enough cards, then this is
			// impossible
			if(successes != required)
				return false;

			// next player is given the chance to bounce all cards this
			// player failed on
			cards.addAll(failedCards);
		}

		// return whether the last player was able to bounce enough
		return successes == required;
	}

	@Override
	public void makeChoices(Game game, Event event, java.util.Map<Parameter, Set> parameters)
	{
		org.rnd.util.NumberRange number = getRange(parameters.get(Parameter.NUMBER));
		java.util.Set<GameObject> choiceParameter = parameters.get(Parameter.CHOICE).getAll(GameObject.class);

		// get the player out of the parameter
		for(Player player: parameters.get(Parameter.PLAYER).getAll(Player.class))
		{
			// offer the choices to the player
			java.util.Collection<GameObject> choices = player.sanitizeAndChoose(game.actualState, number.getLower(), number.getUpper(), choiceParameter, PlayerInterface.ChoiceType.OBJECTS, PlayerInterface.ChooseReason.BOUNCE);
			if(!number.contains(choices.size()))
				event.allChoicesMade = false;
			event.putChoices(player, choices);
		}
	}

	@Override
	public boolean perform(Game game, Event event, java.util.Map<Parameter, Set> parameters)
	{
		boolean allBounced = event.allChoicesMade;
		Set cause = parameters.get(Parameter.CAUSE);
		Set result = new Set();

		// get the player out of the parameter
		for(Player player: parameters.get(Parameter.PLAYER).getAll(Player.class))
		{
			Set bounceThese = event.getChoices(player);

			// perform the bounce event
			java.util.Map<Parameter, Set> bounceParameters = new java.util.HashMap<Parameter, Set>();
			bounceParameters.put(Parameter.CAUSE, cause);
			bounceParameters.put(Parameter.PERMANENT, bounceThese);
			Event bounceEvent = createEvent(game, player + " returns " + bounceThese + " to owners hand.", PUT_INTO_HAND, bounceParameters);
			if(!bounceEvent.perform(event, false))
				allBounced = false;
			result.addAll(bounceEvent.getResult());
		}

		event.setResult(Identity.fromCollection(result));
		return allBounced;
	}
}