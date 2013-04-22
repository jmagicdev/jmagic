package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Sea Gate Oracle")
@Types({Type.CREATURE})
@SubTypes({SubType.HUMAN, SubType.WIZARD})
@ManaCost("2U")
@Printings({@Printings.Printed(ex = Expansion.RISE_OF_THE_ELDRAZI, r = Rarity.COMMON)})
@ColorIdentity({Color.BLUE})
public final class SeaGateOracle extends Card
{
	public static final PlayerInterface.ChooseReason REASON = new PlayerInterface.ChooseReason("SeaGateOracle", "Put a card into your hand.", false);

	/**
	 * @eparam CAUSE: Sea Gate Oracle's trigger
	 * @eparam PLAYER: who controls CAUSE as it's resolving
	 * @eparam CARD: the top two cards of PLAYER's library
	 * @eparam RESULT: empty
	 */
	public static EventType SEA_GATE_ORACLE_EVENT = new EventType("SEA_GATE_ORACLE_EVENT")
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
			Set topOne = parameters.get(Parameter.CARD);
			Set you = parameters.get(Parameter.PLAYER);

			java.util.Map<Parameter, Set> lookParameters = new java.util.HashMap<Parameter, Set>();
			lookParameters.put(Parameter.CAUSE, thisCard);
			lookParameters.put(Parameter.OBJECT, topOne);
			lookParameters.put(Parameter.PLAYER, you);
			createEvent(game, "Look at the top two cards of your library", LOOK, lookParameters).perform(event, false);

			Player player = you.getOne(Player.class);
			Set library = new Set(player.getLibrary(game.actualState));

			java.util.List<?> handChoice = player.sanitizeAndChoose(game.actualState, 1, topOne, PlayerInterface.ChoiceType.OBJECTS, REASON);
			java.util.Map<Parameter, Set> handParameters = new java.util.HashMap<Parameter, Set>();
			handParameters.put(Parameter.CAUSE, thisCard);
			handParameters.put(Parameter.OBJECT, new Set(handChoice.get(0)));
			handParameters.put(Parameter.TO, new Set(player.getHand(game.actualState)));
			Event putIntoHand = createEvent(game, "Put one of those cards into your hand", MOVE_OBJECTS, handParameters);

			Set otherOne = new Set();
			for(GameObject object: topOne.getAll(GameObject.class))
				if(!handChoice.contains(object))
					otherOne.add(object);

			Event putOnBottom = null;
			if(!otherOne.isEmpty())
			{
				java.util.Map<Parameter, Set> bottomParameters = new java.util.HashMap<Parameter, Set>();
				bottomParameters.put(Parameter.CAUSE, thisCard);
				bottomParameters.put(Parameter.OBJECT, otherOne);
				bottomParameters.put(Parameter.TO, library);
				bottomParameters.put(Parameter.INDEX, NEGATIVE_ONE);
				putOnBottom = createEvent(game, "Put the other one on the bottom of your library", MOVE_OBJECTS, bottomParameters);
			}

			putIntoHand.perform(event, false);
			if(putOnBottom != null)
				putOnBottom.perform(event, false);

			event.setResult(Empty.set);
			return true;
		}
	};

	public static final class NotQuiteTellingTime extends EventTriggeredAbility
	{
		public NotQuiteTellingTime(GameState state)
		{
			super(state, "When Sea Gate Oracle enters the battlefield, look at the top two cards of your library. Put one of them into your hand and the other on the bottom of your library.");
			this.addPattern(whenThisEntersTheBattlefield());

			EventFactory look = new EventFactory(SEA_GATE_ORACLE_EVENT, "Look at the top two cards of your library. Put one of them into your hand and the other on the bottom of your library.");
			look.parameters.put(EventType.Parameter.CAUSE, This.instance());
			look.parameters.put(EventType.Parameter.PLAYER, You.instance());
			look.parameters.put(EventType.Parameter.CARD, TopCards.instance(2, LibraryOf.instance(You.instance())));
			this.addEffect(look);
		}
	}

	public SeaGateOracle(GameState state)
	{
		super(state);

		this.setPower(1);
		this.setToughness(3);

		// When Sea Gate Oracle enters the battlefield, look at the top two
		// cards of your library. Put one of them into your hand and the other
		// on the bottom of your library.
		this.addAbility(new NotQuiteTellingTime(state));
	}
}
