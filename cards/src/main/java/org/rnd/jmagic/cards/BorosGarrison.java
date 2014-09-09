package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;

@Name("Boros Garrison")
@Types({Type.LAND})
@ColorIdentity({Color.WHITE, Color.RED})
public final class BorosGarrison extends org.rnd.jmagic.cardTemplates.RavnicaBounceLand
{
	public BorosGarrison(GameState state)
	{
		super(state, 'R', 'W');
	}
}
