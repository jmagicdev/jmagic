package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;

@Name("Dragonskull Summit")
@Types({Type.LAND})
@ColorIdentity({Color.BLACK, Color.RED})
public final class DragonskullSummit extends org.rnd.jmagic.cardTemplates.Magic2010DualLand
{
	public DragonskullSummit(GameState state)
	{
		super(state, SubType.SWAMP, SubType.MOUNTAIN);
	}
}
