package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.expansions.*;

@Name("Seaside Citadel")
@Types({Type.LAND})
@Printings({@Printings.Printed(ex = ShardsOfAlara.class, r = Rarity.UNCOMMON)})
@ColorIdentity({Color.BLUE, Color.WHITE, Color.GREEN})
public final class SeasideCitadel extends org.rnd.jmagic.cardTemplates.ETBTLand
{
	public SeasideCitadel(GameState state)
	{
		super(state, "(GWU)");
	}
}
