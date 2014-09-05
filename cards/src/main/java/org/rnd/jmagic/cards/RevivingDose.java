package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.expansions.*;

@Name("Reviving Dose")
@Types({Type.INSTANT})
@ManaCost("2W")
@Printings({@Printings.Printed(ex = TenthEdition.class, r = Rarity.COMMON), @Printings.Printed(ex = Invasion.class, r = Rarity.COMMON)})
@ColorIdentity({Color.WHITE})
public final class RevivingDose extends Card
{
	public RevivingDose(GameState state)
	{
		super(state);

		this.addEffect(gainLife(You.instance(), 3, "You gain 3 life."));

		this.addEffect(drawCards(You.instance(), 1, "\n\nDraw a card."));
	}
}
