package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.expansions.*;

@Name("Obelisk of Bant")
@ManaCost("3")
@Types({Type.ARTIFACT})
@Printings({@Printings.Printed(ex = ShardsOfAlara.class, r = Rarity.COMMON)})
@ColorIdentity({Color.BLUE, Color.WHITE, Color.GREEN})
public final class ObeliskofBant extends org.rnd.jmagic.cardTemplates.ShardsObelisk
{
	public ObeliskofBant(GameState state)
	{
		super(state, "(GWU)");
	}
}
