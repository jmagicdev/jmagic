package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.expansions.*;

@Name("Entomb")
@Types({Type.INSTANT})
@ManaCost("B")
@Printings({@Printings.Printed(ex = Odyssey.class, r = Rarity.RARE)})
@ColorIdentity({Color.BLACK})
public final class Entomb extends Card
{
	public Entomb(GameState state)
	{
		super(state);

		// Search your library for a card and put that card into your graveyard.
		// Then shuffle your library.

		EventFactory search = new EventFactory(EventType.SEARCH_LIBRARY_AND_PUT_INTO, "Search your library for a card and put that card into your graveyard. Then shuffle your library.");
		search.parameters.put(EventType.Parameter.CAUSE, This.instance());
		search.parameters.put(EventType.Parameter.INDEX, numberGenerator(1));
		search.parameters.put(EventType.Parameter.PLAYER, You.instance());
		search.parameters.put(EventType.Parameter.NUMBER, numberGenerator(1));
		search.parameters.put(EventType.Parameter.TO, GraveyardOf.instance(You.instance()));
		this.addEffect(search);
	}
}
