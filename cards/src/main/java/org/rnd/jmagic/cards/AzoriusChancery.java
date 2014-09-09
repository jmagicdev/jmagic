package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;

@Name("Azorius Chancery")
@Types({Type.LAND})
@ColorIdentity({Color.BLUE, Color.WHITE})
public final class AzoriusChancery extends org.rnd.jmagic.cardTemplates.RavnicaBounceLand
{
	public AzoriusChancery(GameState state)
	{
		super(state, 'W', 'U');
	}
}
