package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.expansions.*;

@Name("Graven Cairns")
@Types({Type.LAND})
@Printings({@Printings.Printed(ex = Shadowmoor.class, r = Rarity.RARE), @Printings.Printed(ex = FutureSight.class, r = Rarity.RARE)})
@ColorIdentity({Color.BLACK, Color.RED})
public final class GravenCairns extends org.rnd.jmagic.cardTemplates.ShadowmoorDualLand
{
	public GravenCairns(GameState state)
	{
		super(state, "B", "R");
	}
}
