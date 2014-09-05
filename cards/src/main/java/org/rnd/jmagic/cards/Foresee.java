package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.expansions.*;

@Name("Foresee")
@Types({Type.SORCERY})
@ManaCost("3U")
@Printings({@Printings.Printed(ex = Magic2011.class, r = Rarity.COMMON), @Printings.Printed(ex = FutureSight.class, r = Rarity.COMMON)})
@ColorIdentity({Color.BLUE})
public final class Foresee extends Card
{
	public Foresee(GameState state)
	{
		super(state);

		this.addEffect(scry(4, "Scry 4."));
		this.addEffect(drawCards(You.instance(), 2, "\n\nDraw 2 cards."));
	}
}
