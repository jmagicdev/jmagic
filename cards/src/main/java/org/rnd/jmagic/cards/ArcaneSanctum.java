package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.expansions.*;

@Name("Arcane Sanctum")
@Types({Type.LAND})
@Printings({@Printings.Printed(ex = ShardsOfAlara.class, r = Rarity.UNCOMMON)})
@ColorIdentity({Color.BLUE, Color.WHITE, Color.BLACK})
public final class ArcaneSanctum extends org.rnd.jmagic.cardTemplates.ETBTLand
{
	public ArcaneSanctum(GameState state)
	{
		super(state, "(WUB)");
	}
}
