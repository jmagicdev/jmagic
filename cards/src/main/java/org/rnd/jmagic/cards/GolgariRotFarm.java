package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;

@Name("Golgari Rot Farm")
@Types({Type.LAND})
@ColorIdentity({Color.BLACK, Color.GREEN})
public final class GolgariRotFarm extends org.rnd.jmagic.cardTemplates.RavnicaBounceLand
{
	public GolgariRotFarm(GameState state)
	{
		super(state, 'B', 'G');
	}
}
