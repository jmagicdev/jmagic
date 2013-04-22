package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Preordain")
@Types({Type.SORCERY})
@ManaCost("U")
@Printings({@Printings.Printed(ex = Expansion.MAGIC_2011, r = Rarity.COMMON)})
@ColorIdentity({Color.BLUE})
public final class Preordain extends Card
{
	public Preordain(GameState state)
	{
		super(state);

		// Scry 2, then draw a card. (To scry 2, look at the top two cards of
		// your library, then put any number of them on the bottom of your
		// library and the rest on top in any order.)
		this.addEffect(scry(2, "Scry 2,"));
		this.addEffect(drawCards(You.instance(), 1, "then draw a card."));
	}
}
