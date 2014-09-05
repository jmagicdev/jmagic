package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.expansions.*;

@Name("Ancient Den")
@Types({Type.LAND, Type.ARTIFACT})
@Printings({@Printings.Printed(ex = Mirrodin.class, r = Rarity.COMMON)})
@ColorIdentity({Color.WHITE})
public final class AncientDen extends Card
{
	public AncientDen(GameState state)
	{
		super(state);

		this.addAbility(new org.rnd.jmagic.abilities.TapForW(state));
	}
}
