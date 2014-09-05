package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.expansions.*;

@Name("Obelisk of Naya")
@ManaCost("3")
@Types({Type.ARTIFACT})
@Printings({@Printings.Printed(ex = ShardsOfAlara.class, r = Rarity.COMMON)})
@ColorIdentity({Color.WHITE, Color.GREEN, Color.RED})
public final class ObeliskofNaya extends org.rnd.jmagic.cardTemplates.ShardsObelisk
{
	public ObeliskofNaya(GameState state)
	{
		super(state, "(RGW)");
	}
}
