package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.expansions.*;

@Name("Cascade Bluffs")
@Types({Type.LAND})
@Printings({@Printings.Printed(ex = Eventide.class, r = Rarity.RARE)})
@ColorIdentity({Color.BLUE, Color.RED})
public final class CascadeBluffs extends org.rnd.jmagic.cardTemplates.ShadowmoorDualLand
{
	public CascadeBluffs(GameState state)
	{
		super(state, "U", "R");
	}
}
