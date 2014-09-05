package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.expansions.*;

@Name("Jungle Shrine")
@Types({Type.LAND})
@Printings({@Printings.Printed(ex = ShardsOfAlara.class, r = Rarity.UNCOMMON)})
@ColorIdentity({Color.WHITE, Color.GREEN, Color.RED})
public final class JungleShrine extends org.rnd.jmagic.cardTemplates.ETBTLand
{
	public JungleShrine(GameState state)
	{
		super(state, "(RGW)");
	}
}
