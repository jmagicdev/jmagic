package org.rnd.jmagic.cards;

import org.rnd.jmagic.cardTemplates.*;
import org.rnd.jmagic.engine.*;

@Name("Drowned Catacomb")
@Types({Type.LAND})
@Printings({@Printings.Printed(ex = Expansion.MAGIC_2013, r = Rarity.RARE), @Printings.Printed(ex = Expansion.MAGIC_2012, r = Rarity.RARE), @Printings.Printed(ex = Expansion.MAGIC_2011, r = Rarity.RARE), @Printings.Printed(ex = Expansion.MAGIC_2010, r = Rarity.RARE)})
@ColorIdentity({Color.BLUE, Color.BLACK})
public final class DrownedCatacomb extends Magic2010DualLand
{
	public DrownedCatacomb(GameState state)
	{
		super(state, SubType.ISLAND, SubType.SWAMP);
	}
}
