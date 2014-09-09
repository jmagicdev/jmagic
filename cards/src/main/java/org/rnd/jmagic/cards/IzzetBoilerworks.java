package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;

@Name("Izzet Boilerworks")
@Types({Type.LAND})
@ColorIdentity({Color.BLUE, Color.RED})
public final class IzzetBoilerworks extends org.rnd.jmagic.cardTemplates.RavnicaBounceLand
{
	public IzzetBoilerworks(GameState state)
	{
		super(state, 'U', 'R');
	}
}
