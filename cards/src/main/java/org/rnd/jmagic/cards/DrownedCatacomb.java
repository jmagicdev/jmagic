package org.rnd.jmagic.cards;

import org.rnd.jmagic.cardTemplates.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.expansions.*;

@Name("Drowned Catacomb")
@Types({Type.LAND})
@Printings({@Printings.Printed(ex = Magic2013.class, r = Rarity.RARE), @Printings.Printed(ex = Magic2012.class, r = Rarity.RARE), @Printings.Printed(ex = Magic2011.class, r = Rarity.RARE), @Printings.Printed(ex = Magic2010.class, r = Rarity.RARE)})
@ColorIdentity({Color.BLUE, Color.BLACK})
public final class DrownedCatacomb extends Magic2010DualLand
{
	public DrownedCatacomb(GameState state)
	{
		super(state, SubType.ISLAND, SubType.SWAMP);
	}
}
