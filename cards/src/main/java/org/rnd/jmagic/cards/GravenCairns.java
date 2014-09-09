package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;

@Name("Graven Cairns")
@Types({Type.LAND})
@ColorIdentity({Color.BLACK, Color.RED})
public final class GravenCairns extends org.rnd.jmagic.cardTemplates.ShadowmoorDualLand
{
	public GravenCairns(GameState state)
	{
		super(state, "B", "R");
	}
}
