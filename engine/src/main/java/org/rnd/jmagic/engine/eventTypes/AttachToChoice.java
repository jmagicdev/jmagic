package org.rnd.jmagic.engine.eventTypes;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

public final class AttachToChoice extends EventType
{
	public static final EventType INSTANCE = new AttachToChoice();

	private AttachToChoice()
	{
		super("ATTACH_TO_CHOICE");
	}

	@Override
	public Parameter affects()
	{
		return Parameter.CHOICE;
	}

	@Override
	public boolean attempt(Game game, Event event, java.util.Map<Parameter, Set> parameters)
	{
		Set object = parameters.get(Parameter.OBJECT);
		Set choices = parameters.get(Parameter.CHOICE);
		Player player = parameters.get(Parameter.PLAYER).getOne(Player.class);

		// reset this players successes to zero
		Set failedCards = new Set();

		while(true)
		{
			if(choices.isEmpty())
				return false;

			GameObject thisCard = choices.getOne(GameObject.class);
			choices.remove(thisCard);
			java.util.Map<Parameter, Set> newParameters = new java.util.HashMap<Parameter, Set>();
			parameters.put(Parameter.OBJECT, object);
			parameters.put(Parameter.TARGET, new Set(thisCard));

			// if the player can attach the card, go to next player.
			// otherwise, give other players the chance to attach the
			// card.
			if(createEvent(game, player + " attaches " + thisCard + " to " + object + ".", ATTACH, newParameters).attempt(event))
				break;
			failedCards.add(thisCard);
		}

		// next player is given the chance to choose all cards this
		// player failed on
		choices.addAll(failedCards);

		return true;
	}

	@Override
	public void makeChoices(Game game, Event event, java.util.Map<Parameter, Set> parameters)
	{
		java.util.Set<AttachableTo> validChoices = parameters.get(Parameter.CHOICE).getAll(AttachableTo.class);
		GameObject object = parameters.get(Parameter.OBJECT).getOne(GameObject.class);
		Player player = parameters.get(Parameter.PLAYER).getOne(Player.class);

		java.util.Iterator<AttachableTo> i = validChoices.iterator();
		while(i.hasNext())
		{
			AttachableTo to = i.next();
			java.util.Map<Parameter, Set> attachParameters = new java.util.HashMap<Parameter, Set>();
			attachParameters.put(Parameter.OBJECT, new Set(object));
			attachParameters.put(Parameter.TARGET, new Set(to));
			Event attachEvent = createEvent(game, player + " attaches " + object + " to " + to + ".", ATTACH, attachParameters);
			if(!attachEvent.attempt(event))
				i.remove();
		}

		PlayerInterface.ChooseParameters<java.io.Serializable> chooseParameters = new PlayerInterface.ChooseParameters<java.io.Serializable>(1, 1, PlayerInterface.ChoiceType.OBJECTS, PlayerInterface.ChooseReason.ATTACH);
		chooseParameters.thisID = object.ID;
		java.util.Collection<AttachableTo> choices = player.sanitizeAndChoose(game.actualState, validChoices, chooseParameters);
		if(choices.isEmpty())
			event.allChoicesMade = false;
		event.putChoices(player, choices);
	}

	@Override
	public boolean perform(Game game, Event event, java.util.Map<Parameter, Set> parameters)
	{
		boolean ret = event.allChoicesMade;
		Set result = new Set();
		Set object = parameters.get(Parameter.OBJECT);
		Player player = parameters.get(Parameter.PLAYER).getOne(Player.class);

		Set choices = event.getChoices(player);
		if(!choices.isEmpty())
		{
			// perform the attach event
			java.util.Map<Parameter, Set> attachParameters = new java.util.HashMap<Parameter, Set>();
			attachParameters.put(Parameter.OBJECT, object);
			attachParameters.put(Parameter.TARGET, choices);
			Event attachEvent = createEvent(game, player + " attaches " + object + " to " + choices + ".", ATTACH, attachParameters);
			if(!attachEvent.perform(event, false))
				ret = false;
			result.addAll(attachEvent.getResult());
		}

		event.setResult(Identity.fromCollection(result));
		return ret;
	}
}