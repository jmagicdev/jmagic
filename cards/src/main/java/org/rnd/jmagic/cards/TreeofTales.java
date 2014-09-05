package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.expansions.*;

@Name("Tree of Tales")
@Types({Type.ARTIFACT, Type.LAND})
@Printings({@Printings.Printed(ex = Mirrodin.class, r = Rarity.COMMON)})
@ColorIdentity({Color.GREEN})
public final class TreeofTales extends Card
{
	public TreeofTales(GameState state)
	{
		super(state);
		this.addAbility(new org.rnd.jmagic.abilities.TapForG(state));
	}
}
