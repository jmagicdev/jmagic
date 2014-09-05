package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.expansions.*;

@Name("Fire-Lit Thicket")
@Types({Type.LAND})
@Printings({@Printings.Printed(ex = Shadowmoor.class, r = Rarity.RARE)})
@ColorIdentity({Color.GREEN, Color.RED})
public final class FireLitThicket extends org.rnd.jmagic.cardTemplates.ShadowmoorDualLand
{
	public FireLitThicket(GameState state)
	{
		super(state, "R", "G");
	}
}
