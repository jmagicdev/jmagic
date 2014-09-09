package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Beast Hunt")
@Types({Type.SORCERY})
@ManaCost("3G")
@ColorIdentity({Color.GREEN})
public final class BeastHunt extends Card
{
	/**
	 * @eparam CAUSE: Beast Hunt
	 * @eparam PLAYER: CAUSE's controller
	 * @eparam OBJECT: top three cards of PLAYER's library
	 */
	public static final EventType BEAST_HUNT_EVENT = new EventType("BEAST_HUNT_EVENT")
	{
		@Override
		public Parameter affects()
		{
			return null;
		}

		@Override
		public boolean perform(Game game, Event event, java.util.Map<Parameter, Set> parameters)
		{
			Set topThree = parameters.get(Parameter.OBJECT);
			Set creatures = new Set();
			for(GameObject o: topThree.getAll(GameObject.class))
				if(o.getTypes().contains(Type.CREATURE))
					creatures.add(o);

			Set beastHunt = parameters.get(Parameter.CAUSE);
			Player you = parameters.get(Parameter.PLAYER).getOne(Player.class);

			java.util.Map<Parameter, Set> handParameters = new java.util.HashMap<Parameter, Set>();
			handParameters.put(Parameter.CAUSE, beastHunt);
			handParameters.put(Parameter.TO, new Set(you.getHand(game.actualState)));
			handParameters.put(Parameter.OBJECT, creatures);
			Event moveToHand = createEvent(game, "Put all creature cards revealed this way into your hand", MOVE_OBJECTS, handParameters);
			moveToHand.perform(event, false);

			you = you.getActual();
			topThree.removeAll(creatures);
			java.util.Map<Parameter, Set> graveyardParameters = new java.util.HashMap<Parameter, Set>();
			graveyardParameters.put(Parameter.CAUSE, beastHunt);
			graveyardParameters.put(Parameter.TO, new Set(you.getGraveyard(game.actualState)));
			graveyardParameters.put(Parameter.OBJECT, topThree);
			Event moveToGraveyard = createEvent(game, "Put all other cards revealed this way into your graveyard", MOVE_OBJECTS, handParameters);
			moveToGraveyard.perform(event, false);

			event.setResult(Empty.set);
			return true;
		}
	};

	public BeastHunt(GameState state)
	{
		super(state);

		// Reveal the top three cards of your library.
		SetGenerator topThree = TopCards.instance(3, LibraryOf.instance(You.instance()));
		EventFactory reveal = new EventFactory(EventType.REVEAL, "Reveal the top three cards of your library.");
		reveal.parameters.put(EventType.Parameter.CAUSE, This.instance());
		reveal.parameters.put(EventType.Parameter.OBJECT, topThree);
		this.addEffect(reveal);

		// Put all creature cards revealed this way into your hand and the rest
		// into your graveyard.
		EventFactory moveCards = new EventFactory(BEAST_HUNT_EVENT, "Put all creature cards revealed this way into your hand and the rest into your graveyard.");
		moveCards.parameters.put(EventType.Parameter.CAUSE, This.instance());
		moveCards.parameters.put(EventType.Parameter.OBJECT, topThree);
		moveCards.parameters.put(EventType.Parameter.PLAYER, You.instance());
		this.addEffect(moveCards);
	}
}
