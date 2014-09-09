package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;

@Name("Underground River")
@Types({Type.LAND})
@ColorIdentity({Color.BLUE, Color.BLACK})
public final class UndergroundRiver extends org.rnd.jmagic.cardTemplates.PainLand
{
	public UndergroundRiver(GameState state)
	{
		super(state, "UB");
	}
}
