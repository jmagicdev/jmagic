package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.expansions.*;

@Name("Worldly Counsel")
@Types({Type.INSTANT})
@ManaCost("1U")
@Printings({@Printings.Printed(ex = Conflux.class, r = Rarity.COMMON), @Printings.Printed(ex = Invasion.class, r = Rarity.COMMON)})
@ColorIdentity({Color.BLUE})
public final class WorldlyCounsel extends Card
{
	public static final PlayerInterface.ChooseReason REASON = new PlayerInterface.ChooseReason("WorldlyCounsel", "Put a card into your hand.", false);

	/**
	 * @eparam CAUSE: Worldly Counsel
	 * @eparam PLAYER: who controls Worldly Counsel as it's resolving
	 * @eparam CARD: the top [domain] cards of PLAYER's library
	 * @eparam RESULT: empty
	 */
	public static EventType WORLDLY_COUNSEL = new EventType("WORLDLY_COUNSEL")
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
			Set topX = parameters.get(Parameter.CARD);
			Set you = parameters.get(Parameter.PLAYER);

			java.util.Map<Parameter, Set> lookParameters = new java.util.HashMap<Parameter, Set>();
			lookParameters.put(Parameter.CAUSE, thisCard);
			lookParameters.put(Parameter.OBJECT, topX);
			lookParameters.put(Parameter.PLAYER, you);
			createEvent(game, "Look at the top X cards of your library, where X is the number of basic land types among lands you control", LOOK, lookParameters).perform(event, true);

			Player player = you.getOne(Player.class).getActual();
			Set library = new Set(player.getLibrary(game.actualState));

			java.util.List<?> handChoice = player.sanitizeAndChoose(game.actualState, 1, topX, PlayerInterface.ChoiceType.OBJECTS, REASON);
			java.util.Map<Parameter, Set> handParameters = new java.util.HashMap<Parameter, Set>();
			handParameters.put(Parameter.CAUSE, thisCard);
			handParameters.put(Parameter.OBJECT, new Set(handChoice.get(0)));
			handParameters.put(Parameter.TO, new Set(player.getHand(game.actualState)));
			Event putIntoHand = createEvent(game, "Put one of those cards into your hand", MOVE_OBJECTS, handParameters);

			Set others = new Set();
			for(GameObject object: topX.getAll(GameObject.class))
				if(!handChoice.contains(object))
					others.add(object);

			Event putOnBottom = null;
			if(!others.isEmpty())
			{
				java.util.Map<Parameter, Set> bottomParameters = new java.util.HashMap<Parameter, Set>();
				bottomParameters.put(Parameter.CAUSE, thisCard);
				bottomParameters.put(Parameter.OBJECT, others);
				bottomParameters.put(Parameter.TO, library);
				bottomParameters.put(Parameter.INDEX, NEGATIVE_ONE);
				putOnBottom = createEvent(game, "Put the rest on the bottom of your library in any order", MOVE_OBJECTS, bottomParameters);
			}

			// These two events are not top level because they happen
			// simultaneously.
			putIntoHand.perform(event, false);
			if(putOnBottom != null)
				putOnBottom.perform(event, false);

			event.setResult(Empty.set);
			return true;
		}
	};

	public WorldlyCounsel(GameState state)
	{
		super(state);

		// Domain \u2014 Look at the top X cards of your library, where X is the
		// number of basic land types among lands you control. Put one of those
		// cards into your hand and the rest on the bottom of your library in
		// any order.
		EventFactory factory = new EventFactory(WORLDLY_COUNSEL, "Look at the top X cards of your library, where X is the number of basic land types among lands you control. Put one of those cards into your hand and the rest on the bottom of your library in any order.");
		factory.parameters.put(EventType.Parameter.CAUSE, This.instance());
		factory.parameters.put(EventType.Parameter.PLAYER, You.instance());
		factory.parameters.put(EventType.Parameter.CARD, TopCards.instance(Domain.instance(You.instance()), LibraryOf.instance(You.instance())));
		this.addEffect(factory);

	}
}
