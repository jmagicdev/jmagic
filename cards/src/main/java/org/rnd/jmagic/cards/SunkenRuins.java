package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.expansions.*;

@Name("Sunken Ruins")
@Types({Type.LAND})
@Printings({@Printings.Printed(ex = Shadowmoor.class, r = Rarity.RARE)})
@ColorIdentity({Color.BLUE, Color.BLACK})
public final class SunkenRuins extends org.rnd.jmagic.cardTemplates.ShadowmoorDualLand
{
	public SunkenRuins(GameState state)
	{
		super(state, "U", "B");
	}
}
