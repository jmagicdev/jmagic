package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.expansions.*;

@Name("Clifftop Retreat")
@Types({Type.LAND})
@Printings({@Printings.Printed(ex = Innistrad.class, r = Rarity.RARE)})
@ColorIdentity({Color.WHITE, Color.RED})
public final class ClifftopRetreat extends org.rnd.jmagic.cardTemplates.Magic2010DualLand
{
	public ClifftopRetreat(GameState state)
	{
		super(state, SubType.MOUNTAIN, SubType.PLAINS);
	}
}
