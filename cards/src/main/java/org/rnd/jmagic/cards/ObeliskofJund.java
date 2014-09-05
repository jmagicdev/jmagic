package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.expansions.*;

@Name("Obelisk of Jund")
@ManaCost("3")
@Types({Type.ARTIFACT})
@Printings({@Printings.Printed(ex = ShardsOfAlara.class, r = Rarity.COMMON)})
@ColorIdentity({Color.GREEN, Color.BLACK, Color.RED})
public final class ObeliskofJund extends org.rnd.jmagic.cardTemplates.ShardsObelisk
{
	public ObeliskofJund(GameState state)
	{
		super(state, "(BRG)");
	}
}
