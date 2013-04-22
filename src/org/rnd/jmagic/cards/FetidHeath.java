package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;

@Name("Fetid Heath")
@Types({Type.LAND})
@Printings({@Printings.Printed(ex = Expansion.EVENTIDE, r = Rarity.RARE)})
@ColorIdentity({Color.WHITE, Color.BLACK})
public final class FetidHeath extends org.rnd.jmagic.cardTemplates.ShadowmoorDualLand
{
	public FetidHeath(GameState state)
	{
		super(state, "W", "B");
	}
}
