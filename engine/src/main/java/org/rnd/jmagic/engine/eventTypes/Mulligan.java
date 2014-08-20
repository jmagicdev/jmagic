package org.rnd.jmagic.engine.eventTypes;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

public final class Mulligan extends EventType
{	public static final EventType INSTANCE = new Mulligan();

	 private Mulligan()
	{
		super("MULLIGAN");
	}

	@Override
	public Parameter affects()
	{
		return Parameter.PLAYER;
	}

	@Override
	public boolean perform(Game game, Event event, java.util.Map<Parameter, Set> parameters)
	{
		Player player = parameters.get(Parameter.PLAYER).getOne(Player.class);
		Zone library = player.getLibrary(game.actualState);
		Zone hand = player.getHand(game.actualState);

		Set cardsInHand = new Set();
		for(GameObject card: hand)
			cardsInHand.add(card);

		Set shuffleObjects = Set.fromCollection(cardsInHand);
		shuffleObjects.add(player);

		java.util.Map<Parameter, Set> shuffleParameters = new java.util.HashMap<Parameter, Set>();
		shuffleParameters.put(Parameter.CAUSE, new Set(game));
		shuffleParameters.put(Parameter.OBJECT, shuffleObjects);
		shuffleParameters.put(Parameter.ZONE, new Set(library));

		boolean hasMulliganed = game.actualState.getTracker(NormalMulliganTracker.class).getValue(game.actualState).contains(player.ID);

		int numberToDraw = hand.objects.size() - ((hasMulliganed || game.actualState.numPlayers() == 2) ? 1 : 0);

		java.util.Map<Parameter, Set> drawParameters = new java.util.HashMap<Parameter, Set>();
		drawParameters.put(Parameter.PLAYER, new Set(player));
		drawParameters.put(Parameter.CAUSE, new Set(game));
		drawParameters.put(Parameter.NUMBER, new Set(numberToDraw));

		createEvent(game, "Shuffle " + cardsInHand + " into " + library + ".", SHUFFLE_INTO_LIBRARY, shuffleParameters).perform(event, true);
		createEvent(game, player + " draws " + numberToDraw + " cards.", DRAW_CARDS, drawParameters).perform(event, true);

		event.setResult(Empty.set);
		return true;
	}
}