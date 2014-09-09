package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;

@Name("Dimir Aqueduct")
@Types({Type.LAND})
@ColorIdentity({Color.BLUE, Color.BLACK})
public final class DimirAqueduct extends org.rnd.jmagic.cardTemplates.RavnicaBounceLand
{
	public DimirAqueduct(GameState state)
	{
		super(state, 'U', 'B');
	}
}
