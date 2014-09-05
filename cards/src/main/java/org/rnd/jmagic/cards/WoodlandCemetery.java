package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.expansions.*;

@Name("Woodland Cemetery")
@Types({Type.LAND})
@Printings({@Printings.Printed(ex = Innistrad.class, r = Rarity.RARE)})
@ColorIdentity({Color.GREEN, Color.BLACK})
public final class WoodlandCemetery extends org.rnd.jmagic.cardTemplates.Magic2010DualLand
{
	public WoodlandCemetery(GameState state)
	{
		super(state, SubType.SWAMP, SubType.FOREST);
	}
}
