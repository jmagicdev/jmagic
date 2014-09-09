package org.rnd.jmagic.cards;

import org.rnd.jmagic.cardTemplates.*;
import org.rnd.jmagic.engine.*;

@Name("Drowned Catacomb")
@Types({Type.LAND})
@ColorIdentity({Color.BLUE, Color.BLACK})
public final class DrownedCatacomb extends Magic2010DualLand
{
	public DrownedCatacomb(GameState state)
	{
		super(state, SubType.ISLAND, SubType.SWAMP);
	}
}
