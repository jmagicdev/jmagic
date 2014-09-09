package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;

@Name("Tree of Tales")
@Types({Type.ARTIFACT, Type.LAND})
@ColorIdentity({Color.GREEN})
public final class TreeofTales extends Card
{
	public TreeofTales(GameState state)
	{
		super(state);
		this.addAbility(new org.rnd.jmagic.abilities.TapForG(state));
	}
}
