package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.expansions.*;

@Name("Ancient Spring")
@Types({Type.LAND})
@Printings({@Printings.Printed(ex = Invasion.class, r = Rarity.COMMON)})
@ColorIdentity({Color.WHITE, Color.BLUE, Color.BLACK})
public final class AncientSpring extends org.rnd.jmagic.cardTemplates.InvasionLand
{
	public AncientSpring(GameState state)
	{
		super(state, Color.BLUE);
	}
}
