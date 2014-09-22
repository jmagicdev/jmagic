package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;

@Name("Tinder Farm")
@Types({Type.LAND})
@ColorIdentity({Color.WHITE, Color.RED, Color.GREEN})
public final class TinderFarm extends org.rnd.jmagic.cardTemplates.InvasionLand
{
	public TinderFarm(GameState state)
	{
		super(state, Color.GREEN);
	}
}
