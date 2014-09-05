package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.expansions.*;

@Name("Fetid Heath")
@Types({Type.LAND})
@Printings({@Printings.Printed(ex = Eventide.class, r = Rarity.RARE)})
@ColorIdentity({Color.WHITE, Color.BLACK})
public final class FetidHeath extends org.rnd.jmagic.cardTemplates.ShadowmoorDualLand
{
	public FetidHeath(GameState state)
	{
		super(state, "W", "B");
	}
}
