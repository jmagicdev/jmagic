package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;

@Name("Ancient Den")
@Types({Type.LAND, Type.ARTIFACT})
@ColorIdentity({Color.WHITE})
public final class AncientDen extends Card
{
	public AncientDen(GameState state)
	{
		super(state);

		this.addAbility(new org.rnd.jmagic.abilities.TapForW(state));
	}
}
