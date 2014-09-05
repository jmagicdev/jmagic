package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.expansions.*;

@Name("Grim Tutor")
@Types({Type.SORCERY})
@ManaCost("1BB")
@Printings({@Printings.Printed(ex = Starter1999.class, r = Rarity.RARE)})
@ColorIdentity({Color.BLACK})
public final class GrimTutor extends Card
{
	public GrimTutor(GameState state)
	{
		super(state);

		// Search your library for a card and put that card into your hand, then
		// shuffle your library. You lose 3 life.
		EventFactory factory = new EventFactory(EventType.SEARCH_LIBRARY_AND_PUT_INTO, "Search your library for a card and put that card into your hand, then shuffle your library.");
		factory.parameters.put(EventType.Parameter.CAUSE, This.instance());
		factory.parameters.put(EventType.Parameter.PLAYER, You.instance());
		factory.parameters.put(EventType.Parameter.NUMBER, numberGenerator(1));
		factory.parameters.put(EventType.Parameter.TO, HandOf.instance(You.instance()));
		this.addEffect(factory);

		this.addEffect(loseLife(You.instance(), 3, "You lose 3 life."));
	}
}
