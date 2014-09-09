package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Telling Time")
@Types({Type.INSTANT})
@ManaCost("1U")
@ColorIdentity({Color.BLUE})
public final class TellingTime extends Card
{
	public static final PlayerInterface.ChooseReason FIRST_REASON = new PlayerInterface.ChooseReason("TellingTime", "Put a card into your hand.", false);
	public static final PlayerInterface.ChooseReason SECOND_REASON = new PlayerInterface.ChooseReason("TellingTime", "Put a card on top of your library.", false);

	/**
	 * @eparam CAUSE: Telling Time
	 * @eparam PLAYER: who controls Telling Time as it's resolving
	 * @eparam CARD: the top three cards of PLAYER's library
	 * @eparam RESULT: empty
	 */
	public static EventType TELLING_TIME = new EventType("TELLING_TIME")
	{

		@Override
		public Parameter affects()
		{
			return null;
		}

		@Override
		public boolean perform(Game game, Event event, java.util.Map<Parameter, Set> parameters)
		{
			Set thisCard = parameters.get(Parameter.CAUSE);
			Set topThree = parameters.get(Parameter.CARD);
			Set you = parameters.get(Parameter.PLAYER);

			java.util.Map<Parameter, Set> lookParameters = new java.util.HashMap<Parameter, Set>();
			lookParameters.put(Parameter.CAUSE, thisCard);
			lookParameters.put(Parameter.OBJECT, topThree);
			lookParameters.put(Parameter.PLAYER, you);
			createEvent(game, "Look at the top three cards of your library", LOOK, lookParameters).perform(event, false);

			Player player = you.getOne(Player.class);
			Set library = new Set(player.getLibrary(game.actualState));

			java.util.List<?> handChoice = player.sanitizeAndChoose(game.actualState, 1, topThree, PlayerInterface.ChoiceType.OBJECTS, FIRST_REASON);
			java.util.Map<Parameter, Set> handParameters = new java.util.HashMap<Parameter, Set>();
			handParameters.put(Parameter.CAUSE, thisCard);
			handParameters.put(Parameter.OBJECT, new Set(handChoice.get(0)));
			handParameters.put(Parameter.TO, new Set(player.getHand(game.actualState)));
			Event putIntoHand = createEvent(game, "Put one of those cards into your hand", MOVE_OBJECTS, handParameters);

			Set otherTwo = new Set();
			for(GameObject object: topThree.getAll(GameObject.class))
				if(!handChoice.contains(object))
					otherTwo.add(object);

			Event putOnTop = null;
			Event putOnBottom = null;
			if(!otherTwo.isEmpty())
			{
				java.util.List<?> topChoice = player.sanitizeAndChoose(game.actualState, 1, otherTwo, PlayerInterface.ChoiceType.OBJECTS, SECOND_REASON);
				java.util.Map<Parameter, Set> topParameters = new java.util.HashMap<Parameter, Set>();
				topParameters.put(Parameter.CAUSE, thisCard);
				topParameters.put(Parameter.OBJECT, new Set(topChoice.get(0)));
				topParameters.put(Parameter.TO, library);
				topParameters.put(Parameter.INDEX, ONE);
				putOnTop = createEvent(game, "Put one on top of your library", MOVE_OBJECTS, topParameters);

				Set lastCard = new Set();
				for(GameObject object: otherTwo.getAll(GameObject.class))
					if(!topChoice.contains(object))
						lastCard.add(object);

				java.util.Map<Parameter, Set> bottomParameters = new java.util.HashMap<Parameter, Set>();
				bottomParameters.put(Parameter.CAUSE, thisCard);
				bottomParameters.put(Parameter.OBJECT, lastCard);
				bottomParameters.put(Parameter.TO, library);
				bottomParameters.put(Parameter.INDEX, NEGATIVE_ONE);
				putOnBottom = createEvent(game, "Put one on the bottom of your library", MOVE_OBJECTS, bottomParameters);
			}

			putIntoHand.perform(event, false);
			if(putOnTop != null)
				putOnTop.perform(event, false);
			if(putOnBottom != null)
				putOnBottom.perform(event, false);

			event.setResult(Empty.set);
			return true;
		}
	};

	public TellingTime(GameState state)
	{
		super(state);

		EventType.ParameterMap parameters = new EventType.ParameterMap();
		parameters.put(EventType.Parameter.CAUSE, This.instance());
		parameters.put(EventType.Parameter.PLAYER, You.instance());
		parameters.put(EventType.Parameter.CARD, TopCards.instance(3, LibraryOf.instance(You.instance())));
		this.addEffect(new EventFactory(TELLING_TIME, parameters, "Look at the top three cards of your library. Put one of those cards into your hand, one on top of your library, and one on the bottom of your library."));
	}
}
