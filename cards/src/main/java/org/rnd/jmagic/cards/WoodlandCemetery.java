package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;

@Name("Woodland Cemetery")
@Types({Type.LAND})
@ColorIdentity({Color.GREEN, Color.BLACK})
public final class WoodlandCemetery extends org.rnd.jmagic.cardTemplates.Magic2010DualLand
{
	public WoodlandCemetery(GameState state)
	{
		super(state, SubType.SWAMP, SubType.FOREST);
	}
}
