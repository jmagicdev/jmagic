package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Trapmaker's Snare")
@Types({Type.INSTANT})
@ManaCost("1U")
@ColorIdentity({Color.BLUE})
public final class TrapmakersSnare extends Card
{
	public TrapmakersSnare(GameState state)
	{
		super(state);

		// Search your library for a Trap card, reveal it, and put it into your
		// hand. Then shuffle your library.
		EventFactory search = new EventFactory(EventType.SEARCH_LIBRARY_AND_PUT_INTO, "Search your library for a Trap card, reveal it, and put it into your hand. Then shuffle your library.");
		search.parameters.put(EventType.Parameter.CAUSE, This.instance());
		search.parameters.put(EventType.Parameter.PLAYER, You.instance());
		search.parameters.put(EventType.Parameter.NUMBER, numberGenerator(1));
		search.parameters.put(EventType.Parameter.TO, HandOf.instance(You.instance()));
		search.parameters.put(EventType.Parameter.TYPE, Identity.instance(HasSubType.instance(SubType.TRAP)));
		this.addEffect(search);
	}
}
