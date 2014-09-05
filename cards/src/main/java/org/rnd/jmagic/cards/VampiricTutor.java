package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.expansions.*;

@Name("Vampiric Tutor")
@Types({Type.INSTANT})
@ManaCost("B")
@Printings({@Printings.Printed(ex = ClassicSixthEdition.class, r = Rarity.RARE), @Printings.Printed(ex = Visions.class, r = Rarity.RARE)})
@ColorIdentity({Color.BLACK})
public final class VampiricTutor extends Card
{
	public VampiricTutor(GameState state)
	{
		super(state);

		// Search your library for a card, then shuffle your library and put
		// that card on top of it.
		EventType.ParameterMap parameters = new EventType.ParameterMap();
		parameters.put(EventType.Parameter.CAUSE, This.instance());
		parameters.put(EventType.Parameter.PLAYER, You.instance());
		this.addEffect(new EventFactory(EventType.SEARCH_LIBRARY_AND_PUT_ON_TOP, parameters, "Search your library for a card, then shuffle your library and put that card on top of it."));

		// You lose 2 life.
		this.addEffect(loseLife(You.instance(), 2, "You lose 2 life."));
	}
}
