package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;

@Name("Selesnya Sanctuary")
@Types({Type.LAND})
@ColorIdentity({Color.WHITE, Color.GREEN})
public final class SelesnyaSanctuary extends org.rnd.jmagic.cardTemplates.RavnicaBounceLand
{
	public SelesnyaSanctuary(GameState state)
	{
		super(state, 'G', 'W');
	}
}
