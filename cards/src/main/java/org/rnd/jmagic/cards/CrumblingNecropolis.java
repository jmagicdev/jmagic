package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;

@Name("Crumbling Necropolis")
@Types({Type.LAND})
@Printings({@Printings.Printed(ex = Expansion.SHARDS_OF_ALARA, r = Rarity.UNCOMMON)})
@ColorIdentity({Color.BLUE, Color.BLACK, Color.RED})
public final class CrumblingNecropolis extends org.rnd.jmagic.cardTemplates.ETBTLand
{
	public CrumblingNecropolis(GameState state)
	{
		super(state, "(UBR)");
	}
}
