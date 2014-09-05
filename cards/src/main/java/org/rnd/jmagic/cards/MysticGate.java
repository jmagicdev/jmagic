package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.expansions.*;

@Name("Mystic Gate")
@Types({Type.LAND})
@Printings({@Printings.Printed(ex = Shadowmoor.class, r = Rarity.RARE)})
@ColorIdentity({Color.BLUE, Color.WHITE})
public final class MysticGate extends org.rnd.jmagic.cardTemplates.ShadowmoorDualLand
{
	public MysticGate(GameState state)
	{
		super(state, "W", "U");
	}
}
