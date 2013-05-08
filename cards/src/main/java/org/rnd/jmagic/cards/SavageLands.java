package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;

@Name("Savage Lands")
@Types({Type.LAND})
@Printings({@Printings.Printed(ex = Expansion.SHARDS_OF_ALARA, r = Rarity.UNCOMMON)})
@ColorIdentity({Color.GREEN, Color.BLACK, Color.RED})
public final class SavageLands extends org.rnd.jmagic.cardTemplates.ETBTLand
{
	public SavageLands(GameState state)
	{
		super(state, "(BRG)");
	}
}
