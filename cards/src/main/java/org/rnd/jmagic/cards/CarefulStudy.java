package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.expansions.*;

@Name("Careful Study")
@Types({Type.SORCERY})
@ManaCost("U")
@Printings({@Printings.Printed(ex = Odyssey.class, r = Rarity.COMMON)})
@ColorIdentity({Color.BLUE})
public final class CarefulStudy extends Card
{
	public CarefulStudy(GameState state)
	{
		super(state);

		// Draw two cards, then discard two cards.
		this.addEffect(drawCards(You.instance(), 2, "Draw two cards,"));
		this.addEffect(discardCards(You.instance(), 2, "then discard two cards."));
	}
}
