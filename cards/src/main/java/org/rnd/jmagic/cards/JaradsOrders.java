package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Jarad's Orders")
@Types({Type.SORCERY})
@ManaCost("2BG")
@ColorIdentity({Color.GREEN, Color.BLACK})
public final class JaradsOrders extends Card
{
	public JaradsOrders(GameState state)
	{
		super(state);

		// Search your library for up to two creature cards and reveal them.
		EventFactory search = new EventFactory(EventType.SEARCH, "Search your library for up to two creature cards and reveal them.");
		search.parameters.put(EventType.Parameter.CAUSE, This.instance());
		search.parameters.put(EventType.Parameter.PLAYER, You.instance());
		search.parameters.put(EventType.Parameter.NUMBER, Between.instance(0, 2));
		search.parameters.put(EventType.Parameter.CARD, LibraryOf.instance(You.instance()));
		search.parameters.put(EventType.Parameter.TYPE, HasType.instance(Type.CREATURE));
		this.addEffect(search);

		// Put one into your hand
		EventFactory toHand = new EventFactory(EventType.MOVE_CHOICE, "Put one into your hand");
		toHand.parameters.put(EventType.Parameter.CAUSE, This.instance());
		toHand.parameters.put(EventType.Parameter.NUMBER, numberGenerator(1));
		toHand.parameters.put(EventType.Parameter.OBJECT, EffectResult.instance(search));
		toHand.parameters.put(EventType.Parameter.CHOICE, Identity.instance(PlayerInterface.ChooseReason.PUT_INTO_HAND));
		toHand.parameters.put(EventType.Parameter.TO, HandOf.instance(You.instance()));
		toHand.parameters.put(EventType.Parameter.PLAYER, You.instance());
		this.addEffect(toHand);

		// and the other into your graveyard.
		this.addEffect(putIntoGraveyard(EffectResult.instance(search), "and the other into your graveyard."));

		// Then shuffle your library.
		this.addEffect(shuffleLibrary(You.instance(), "Then shuffle your library."));
	}
}
