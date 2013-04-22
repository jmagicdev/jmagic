package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Distant Memories")
@Types({Type.SORCERY})
@ManaCost("2UU")
@Printings({@Printings.Printed(ex = Expansion.MIRRODIN_BESIEGED, r = Rarity.RARE)})
@ColorIdentity({Color.BLUE})
public final class DistantMemories extends Card
{
	public DistantMemories(GameState state)
	{
		super(state);

		// Search your library for a card, exile it, then shuffle your library.
		EventFactory search = new EventFactory(EventType.SEARCH_LIBRARY_AND_PUT_INTO, "Search your library for a card, exile it, then shuffle your library.");
		search.parameters.put(EventType.Parameter.CAUSE, This.instance());
		search.parameters.put(EventType.Parameter.PLAYER, You.instance());
		search.parameters.put(EventType.Parameter.NUMBER, numberGenerator(1));
		search.parameters.put(EventType.Parameter.TO, ExileZone.instance());
		this.addEffect(search);

		// Any opponent may have you put that card into your hand. If no player
		// does, you draw three cards.
		SetGenerator thatCard = EffectResult.instance(search);

		EventFactory thatOpponentHasYouTake = new EventFactory(EventType.MOVE_OBJECTS, "That player puts that card into their hand");
		thatOpponentHasYouTake.parameters.put(EventType.Parameter.CAUSE, This.instance());
		thatOpponentHasYouTake.parameters.put(EventType.Parameter.TO, HandOf.instance(You.instance()));
		thatOpponentHasYouTake.parameters.put(EventType.Parameter.OBJECT, thatCard);

		SetGenerator eachPlayer = DynamicEvaluation.instance();
		EventFactory thatOpponentMayHaveYouTake = playerMay(eachPlayer, thatOpponentHasYouTake, "An opponent may have you put that card into your hand");

		EventFactory anyOpponentHasYouTake = new EventFactory(FOR_EACH_PLAYER, "Any opponent may have you put that card into your hand");
		anyOpponentHasYouTake.parameters.put(EventType.Parameter.PLAYER, OpponentsOf.instance(You.instance()));
		anyOpponentHasYouTake.parameters.put(EventType.Parameter.TARGET, Identity.instance(eachPlayer));
		anyOpponentHasYouTake.parameters.put(EventType.Parameter.EFFECT, Identity.instance(thatOpponentMayHaveYouTake));

		EventFactory draw = drawCards(You.instance(), 3, "You draw three cards");

		EventFactory takeOrDraw = new EventFactory(EventType.IF_EVENT_THEN_ELSE, "Any opponent may have you put that card into your hand. If no player does, you draw three cards.");
		takeOrDraw.parameters.put(EventType.Parameter.IF, Identity.instance(anyOpponentHasYouTake));
		takeOrDraw.parameters.put(EventType.Parameter.ELSE, Identity.instance(draw));
		this.addEffect(takeOrDraw);
	}
}
