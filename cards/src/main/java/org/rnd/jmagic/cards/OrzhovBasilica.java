package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;

@Name("Orzhov Basilica")
@Types({Type.LAND})
@ColorIdentity({Color.WHITE, Color.BLACK})
public final class OrzhovBasilica extends org.rnd.jmagic.cardTemplates.RavnicaBounceLand
{
	public OrzhovBasilica(GameState state)
	{
		super(state, 'W', 'B');
	}
}
