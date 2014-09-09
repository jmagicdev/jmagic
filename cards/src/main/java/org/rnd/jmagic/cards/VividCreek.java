package org.rnd.jmagic.cards;

import org.rnd.jmagic.cardTemplates.*;
import org.rnd.jmagic.engine.*;

@Name("Vivid Creek")
@Types({Type.LAND})
@ColorIdentity({Color.BLUE})
public final class VividCreek extends VividLand
{
	public VividCreek(GameState state)
	{
		super(state, Color.BLUE);
	}
}
