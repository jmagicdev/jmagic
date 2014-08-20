package org.rnd.jmagic.engine.eventTypes;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

public final class RevealRandomFromHand extends EventType
{	public static final EventType INSTANCE = new RevealRandomFromHand();

	 private RevealRandomFromHand()
	{
		super("REVEAL_RANDOM_FROM_HAND");
	}

	@Override
	public Parameter affects()
	{
		return Parameter.PLAYER;
	}

	@Override
	public boolean perform(Game game, Event event, java.util.Map<Parameter, Set> parameters)
	{
		Set result = new Set();
		int number = Sum.get(parameters.get(Parameter.NUMBER));
		boolean ret = true;

		for(Player player: parameters.get(Parameter.PLAYER).getAll(Player.class))
		{
			java.util.List<GameObject> reveal = new java.util.LinkedList<GameObject>();
			java.util.List<GameObject> hand = new java.util.LinkedList<GameObject>(player.getHand(game.actualState).objects);
			java.util.Collections.shuffle(hand);

			if(hand.size() <= number)
				reveal.addAll(hand);
			else
				for(int i = 0; i < number; i++)
					reveal.add(hand.remove(0));

			if(reveal.size() < number)
				ret = false;

			java.util.Map<Parameter, Set> revealParameters = new java.util.HashMap<Parameter, Set>();
			revealParameters.put(EventType.Parameter.CAUSE, parameters.get(Parameter.CAUSE));
			revealParameters.put(EventType.Parameter.OBJECT, Set.fromCollection(reveal));
			Event revealEvent = createEvent(game, player + " reveals " + reveal, EventType.REVEAL, revealParameters);

			if(!revealEvent.perform(event, false))
				ret = false;

			result.addAll(revealEvent.getResult());
		}

		event.setResult(result);

		return ret;
	}
}