package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;

@Name("Obelisk of Naya")
@ManaCost("3")
@Types({Type.ARTIFACT})
@Printings({@Printings.Printed(ex = Expansion.SHARDS_OF_ALARA, r = Rarity.COMMON)})
@ColorIdentity({Color.WHITE, Color.GREEN, Color.RED})
public final class ObeliskofNaya extends org.rnd.jmagic.cardTemplates.ShardsObelisk
{
	public ObeliskofNaya(GameState state)
	{
		super(state, "(RGW)");
	}
}
