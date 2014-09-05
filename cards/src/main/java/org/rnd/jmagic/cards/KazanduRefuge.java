package org.rnd.jmagic.cards;

import org.rnd.jmagic.cardTemplates.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.expansions.*;

@Name("Kazandu Refuge")
@Types({Type.LAND})
@Printings({@Printings.Printed(ex = MagicTheGatheringCommander.class, r = Rarity.UNCOMMON), @Printings.Printed(ex = Zendikar.class, r = Rarity.UNCOMMON)})
@ColorIdentity({Color.GREEN, Color.RED})
public final class KazanduRefuge extends ZendikarLifeLand
{
	public KazanduRefuge(GameState state)
	{
		super(state, "(RG)");
	}
}
