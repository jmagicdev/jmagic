package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;

@Name("Graven Cairns")
@Types({Type.LAND})
@Printings({@Printings.Printed(ex = Expansion.SHADOWMOOR, r = Rarity.RARE), @Printings.Printed(ex = Expansion.FUTURE_SIGHT, r = Rarity.RARE)})
@ColorIdentity({Color.BLACK, Color.RED})
public final class GravenCairns extends org.rnd.jmagic.cardTemplates.ShadowmoorDualLand
{
	public GravenCairns(GameState state)
	{
		super(state, "B", "R");
	}
}
