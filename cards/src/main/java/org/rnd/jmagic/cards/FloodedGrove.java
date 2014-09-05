package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.expansions.*;

@Name("Flooded Grove")
@Types({Type.LAND})
@Printings({@Printings.Printed(ex = Eventide.class, r = Rarity.RARE)})
@ColorIdentity({Color.BLUE, Color.GREEN})
public final class FloodedGrove extends org.rnd.jmagic.cardTemplates.ShadowmoorDualLand
{
	public FloodedGrove(GameState state)
	{
		super(state, "G", "U");
	}
}
