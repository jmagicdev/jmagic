package org.rnd.jmagic.cards;

import org.rnd.jmagic.cardTemplates.*;
import org.rnd.jmagic.engine.*;

@Name("Vivid Meadow")
@Types({Type.LAND})
@ColorIdentity({Color.WHITE})
public final class VividMeadow extends VividLand
{
	public VividMeadow(GameState state)
	{
		super(state, Color.WHITE);
	}
}
