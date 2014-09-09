package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;

@Name("Yavimaya Coast")
@Types({Type.LAND})
@ColorIdentity({Color.BLUE, Color.GREEN})
public final class YavimayaCoast extends org.rnd.jmagic.cardTemplates.PainLand
{
	public YavimayaCoast(GameState state)
	{
		super(state, "GU");
	}
}
