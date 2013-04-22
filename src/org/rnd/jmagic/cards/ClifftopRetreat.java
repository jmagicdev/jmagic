package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;

@Name("Clifftop Retreat")
@Types({Type.LAND})
@Printings({@Printings.Printed(ex = Expansion.INNISTRAD, r = Rarity.RARE)})
@ColorIdentity({Color.WHITE, Color.RED})
public final class ClifftopRetreat extends org.rnd.jmagic.cardTemplates.Magic2010DualLand
{
	public ClifftopRetreat(GameState state)
	{
		super(state, SubType.MOUNTAIN, SubType.PLAINS);
	}
}
