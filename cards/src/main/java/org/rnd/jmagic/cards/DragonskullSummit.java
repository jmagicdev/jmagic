package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.expansions.*;

@Name("Dragonskull Summit")
@Types({Type.LAND})
@Printings({@Printings.Printed(ex = Magic2013.class, r = Rarity.RARE), @Printings.Printed(ex = Magic2012.class, r = Rarity.RARE), @Printings.Printed(ex = Magic2011.class, r = Rarity.RARE), @Printings.Printed(ex = Magic2010.class, r = Rarity.RARE)})
@ColorIdentity({Color.BLACK, Color.RED})
public final class DragonskullSummit extends org.rnd.jmagic.cardTemplates.Magic2010DualLand
{
	public DragonskullSummit(GameState state)
	{
		super(state, SubType.SWAMP, SubType.MOUNTAIN);
	}
}
