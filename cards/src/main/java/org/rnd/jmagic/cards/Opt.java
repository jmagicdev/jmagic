package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.expansions.*;

@Name("Opt")
@Types({Type.INSTANT})
@ManaCost("U")
@Printings({@Printings.Printed(ex = Invasion.class, r = Rarity.COMMON)})
@ColorIdentity({Color.BLUE})
public final class Opt extends Card
{
	public static final EventType OPT_EVENT = new EventType("OPT_EVENT")
	{
		@Override
		public Parameter affects()
		{
			return null;
		}

		/**
		 * @eparam CAUSE: Opt
		 * @eparam PLAYER: The controller of CAUSE.
		 * @eparam OBJECT: The top card of PLAYER's library.
		 * @eparam ZONE: PLAYER's library
		 * @eparam RESULT: empty
		 */
		@Override
		public boolean perform(Game game, Event event, java.util.Map<Parameter, Set> parameters)
		{
			Set cause = parameters.get(Parameter.CAUSE);
			Set topCard = parameters.get(Parameter.OBJECT);
			Set you = parameters.get(Parameter.PLAYER);
			Set library = parameters.get(Parameter.ZONE);

			java.util.Map<Parameter, Set> lookParameters = new java.util.HashMap<Parameter, Set>();
			lookParameters.put(Parameter.CAUSE, cause);
			lookParameters.put(Parameter.OBJECT, topCard);
			lookParameters.put(Parameter.PLAYER, you);
			createEvent(game, "Look at the top card of your library.", LOOK, lookParameters).perform(event, false);

			EventType.ParameterMap moveParameters = new EventType.ParameterMap();
			moveParameters.put(Parameter.CAUSE, Identity.fromCollection(cause));
			moveParameters.put(Parameter.TO, Identity.fromCollection(library));
			moveParameters.put(Parameter.INDEX, numberGenerator(-1));
			moveParameters.put(Parameter.OBJECT, Identity.fromCollection(topCard));
			Set move = new Set(new EventFactory(MOVE_OBJECTS, moveParameters, "Put that card on the bottom of your library."));

			java.util.Map<Parameter, Set> mayParameters = new java.util.HashMap<Parameter, Set>();
			mayParameters.put(Parameter.PLAYER, you);
			mayParameters.put(Parameter.EVENT, move);
			createEvent(game, "You may put that card on the bottom of your library.", PLAYER_MAY, mayParameters).perform(event, false);

			event.setResult(Empty.set);

			return true;
		}
	};

	public Opt(GameState state)
	{
		super(state);

		EventType.ParameterMap parameters = new EventType.ParameterMap();
		parameters.put(EventType.Parameter.CAUSE, This.instance());
		parameters.put(EventType.Parameter.PLAYER, You.instance());
		parameters.put(EventType.Parameter.OBJECT, TopCards.instance(1, LibraryOf.instance(You.instance())));
		parameters.put(EventType.Parameter.ZONE, LibraryOf.instance(You.instance()));
		this.addEffect(new EventFactory(OPT_EVENT, parameters, "Look at the top card of your library. You may put that card on the  bottom of your library."));

		this.addEffect(drawCards(You.instance(), 1, "\n\nDraw a card."));
	}
}
