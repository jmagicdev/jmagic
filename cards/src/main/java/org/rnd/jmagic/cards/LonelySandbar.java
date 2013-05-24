package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;

@Name("Lonely Sandbar")
@Types({Type.LAND})
@Printings({@Printings.Printed(ex = Expansion.COMMANDER, r = Rarity.COMMON), @Printings.Printed(ex = Expansion.ONSLAUGHT, r = Rarity.COMMON)})
@ColorIdentity({Color.BLUE})
public final class LonelySandbar extends org.rnd.jmagic.cardTemplates.OnslaughtCyclingLand
{
	public LonelySandbar(GameState state)
	{
		super(state, "(U)");
	}
}
