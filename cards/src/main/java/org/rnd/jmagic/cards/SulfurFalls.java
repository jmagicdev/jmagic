package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;

@Name("Sulfur Falls")
@Types({Type.LAND})
@ColorIdentity({Color.BLUE, Color.RED})
public final class SulfurFalls extends org.rnd.jmagic.cardTemplates.Magic2010DualLand
{
	public SulfurFalls(GameState state)
	{
		super(state, SubType.ISLAND, SubType.MOUNTAIN);
	}
}
