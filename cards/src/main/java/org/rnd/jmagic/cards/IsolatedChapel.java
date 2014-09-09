package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;

@Name("Isolated Chapel")
@Types({Type.LAND})
@ColorIdentity({Color.WHITE, Color.BLACK})
public final class IsolatedChapel extends org.rnd.jmagic.cardTemplates.Magic2010DualLand
{
	public IsolatedChapel(GameState state)
	{
		super(state, SubType.PLAINS, SubType.SWAMP);
	}
}
