package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.expansions.*;

@Name("Tidings")
@Types({Type.SORCERY})
@ManaCost("3UU")
@Printings({@Printings.Printed(ex = TenthEdition.class, r = Rarity.UNCOMMON), @Printings.Printed(ex = NinthEdition.class, r = Rarity.UNCOMMON), @Printings.Printed(ex = Starter1999.class, r = Rarity.UNCOMMON)})
@ColorIdentity({Color.BLUE})
public final class Tidings extends Card
{
	public Tidings(GameState state)
	{
		super(state);

		this.addEffect(drawCards(You.instance(), 4, "Draw four cards."));
	}
}
