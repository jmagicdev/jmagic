package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.expansions.*;

@Name("Divination")
@Types({Type.SORCERY})
@ManaCost("2U")
@Printings({@Printings.Printed(ex = Magic2013.class, r = Rarity.COMMON), @Printings.Printed(ex = DarkAscension.class, r = Rarity.COMMON), @Printings.Printed(ex = Magic2012.class, r = Rarity.COMMON), @Printings.Printed(ex = Magic2010.class, r = Rarity.COMMON)})
@ColorIdentity({Color.BLUE})
public final class Divination extends Card
{
	public Divination(GameState state)
	{
		super(state);

		// Draw two cards.
		this.addEffect(drawCards(You.instance(), 2, "Draw two cards."));
	}
}
