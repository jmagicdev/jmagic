package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.expansions.*;

@Name("Savage Lands")
@Types({Type.LAND})
@Printings({@Printings.Printed(ex = ShardsOfAlara.class, r = Rarity.UNCOMMON)})
@ColorIdentity({Color.GREEN, Color.BLACK, Color.RED})
public final class SavageLands extends org.rnd.jmagic.cardTemplates.ETBTLand
{
	public SavageLands(GameState state)
	{
		super(state, "(BRG)");
	}
}
