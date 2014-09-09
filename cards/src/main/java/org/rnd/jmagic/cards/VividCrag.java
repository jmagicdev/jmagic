package org.rnd.jmagic.cards;

import org.rnd.jmagic.cardTemplates.*;
import org.rnd.jmagic.engine.*;

@Name("Vivid Crag")
@Types({Type.LAND})
@ColorIdentity({Color.RED})
public final class VividCrag extends VividLand
{
	public VividCrag(GameState state)
	{
		super(state, Color.RED);
	}
}
