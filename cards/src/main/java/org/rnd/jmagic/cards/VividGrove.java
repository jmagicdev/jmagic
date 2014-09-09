package org.rnd.jmagic.cards;

import org.rnd.jmagic.cardTemplates.*;
import org.rnd.jmagic.engine.*;

@Name("Vivid Grove")
@Types({Type.LAND})
@ColorIdentity({Color.GREEN})
public final class VividGrove extends VividLand
{
	public VividGrove(GameState state)
	{
		super(state, Color.GREEN);
	}
}
