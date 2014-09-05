package org.rnd.jmagic.cards;

import org.rnd.jmagic.cardTemplates.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.expansions.*;

@Name("Akoum Refuge")
@Types({Type.LAND})
@Printings({@Printings.Printed(ex = MagicTheGatheringCommander.class, r = Rarity.UNCOMMON), @Printings.Printed(ex = Zendikar.class, r = Rarity.UNCOMMON)})
@ColorIdentity({Color.BLACK, Color.RED})
public final class AkoumRefuge extends ZendikarLifeLand
{
	public AkoumRefuge(GameState state)
	{
		super(state, "(BR)");
	}
}
