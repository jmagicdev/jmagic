package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;

@Name("Ancient Spring")
@Types({Type.LAND})
@ColorIdentity({Color.WHITE, Color.BLUE, Color.BLACK})
public final class AncientSpring extends org.rnd.jmagic.cardTemplates.InvasionLand
{
	public AncientSpring(GameState state)
	{
		super(state, Color.BLUE);
	}
}
