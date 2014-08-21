package org.rnd.jmagic.engine.eventTypes;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

public final class DiscardTo extends EventType
{
	public static final EventType INSTANCE = new DiscardTo();

	private DiscardTo()
	{
		super("DISCARD_TO");
	}

	@Override
	public Parameter affects()
	{
		return Parameter.PLAYER;
	}

	@Override
	public boolean perform(Game game, Event event, java.util.Map<Parameter, Set> parameters)
	{
		// get the number of cards out of the parameter
		int numberOfCards = Sum.get(parameters.get(Parameter.NUMBER));

		if(numberOfCards < 0)
			numberOfCards = 0;

		Set cause = parameters.get(Parameter.CAUSE);

		boolean allDiscarded = true;
		Set result = new Set();

		// get the player out of the actee and get the ID's of the cards in
		// their hand
		for(Player player: parameters.get(Parameter.PLAYER).getAll(Player.class))
		{
			int numberToDiscard = player.getHand(game.actualState).objects.size() - numberOfCards;
			if(numberToDiscard <= 0)
				continue;

			java.util.Map<Parameter, Set> discardParameters = new java.util.HashMap<Parameter, Set>();
			discardParameters.put(Parameter.CAUSE, cause);
			discardParameters.put(Parameter.PLAYER, new Set(player));
			discardParameters.put(Parameter.NUMBER, new Set(numberToDiscard));

			Event discard = createEvent(game, player + " discards " + numberToDiscard + " card" + (numberToDiscard == 1 ? "" : "s."), DISCARD_CHOICE, discardParameters);
			if(!discard.perform(event, false))
				result.addAll(discard.getResult());
			allDiscarded = false;
		}

		event.setResult(Identity.fromCollection(result));
		return allDiscarded;
	}
}