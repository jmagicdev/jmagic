package org.rnd.jmagic.cards;

import org.rnd.jmagic.cardTemplates.*;
import org.rnd.jmagic.engine.*;

@Name("Vivid Marsh")
@Types({Type.LAND})
@ColorIdentity({Color.BLACK})
public final class VividMarsh extends VividLand
{
	public VividMarsh(GameState state)
	{
		super(state, Color.BLACK);
	}
}
