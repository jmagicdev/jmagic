package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Tidings")
@Types({Type.SORCERY})
@ManaCost("3UU")
@Printings({@Printings.Printed(ex = Expansion.TENTH_EDITION, r = Rarity.UNCOMMON), @Printings.Printed(ex = Expansion.NINTH_EDITION, r = Rarity.UNCOMMON), @Printings.Printed(ex = Expansion.STARTER, r = Rarity.UNCOMMON)})
@ColorIdentity({Color.BLUE})
public final class Tidings extends Card
{
	public Tidings(GameState state)
	{
		super(state);

		this.addEffect(drawCards(You.instance(), 4, "Draw four cards."));
	}
}
