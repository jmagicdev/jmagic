package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;

@Name("Gruul Turf")
@Types({Type.LAND})
@ColorIdentity({Color.GREEN, Color.RED})
public final class GruulTurf extends org.rnd.jmagic.cardTemplates.RavnicaBounceLand
{
	public GruulTurf(GameState state)
	{
		super(state, 'R', 'G');
	}
}
