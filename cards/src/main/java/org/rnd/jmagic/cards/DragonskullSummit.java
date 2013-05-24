package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;

@Name("Dragonskull Summit")
@Types({Type.LAND})
@Printings({@Printings.Printed(ex = Expansion.MAGIC_2013, r = Rarity.RARE), @Printings.Printed(ex = Expansion.MAGIC_2012, r = Rarity.RARE), @Printings.Printed(ex = Expansion.MAGIC_2011, r = Rarity.RARE), @Printings.Printed(ex = Expansion.MAGIC_2010, r = Rarity.RARE)})
@ColorIdentity({Color.BLACK, Color.RED})
public final class DragonskullSummit extends org.rnd.jmagic.cardTemplates.Magic2010DualLand
{
	public DragonskullSummit(GameState state)
	{
		super(state, SubType.SWAMP, SubType.MOUNTAIN);
	}
}
