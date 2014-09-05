package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.expansions.*;

@Name("Twilight Mire")
@Types({Type.LAND})
@Printings({@Printings.Printed(ex = Eventide.class, r = Rarity.RARE)})
@ColorIdentity({Color.GREEN, Color.BLACK})
public final class TwilightMire extends org.rnd.jmagic.cardTemplates.ShadowmoorDualLand
{
	public TwilightMire(GameState state)
	{
		super(state, "B", "G");
	}
}
