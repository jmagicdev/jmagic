package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;

@Name("Barren Moor")
@Types({Type.LAND})
@Printings({@Printings.Printed(ex = Expansion.COMMANDER, r = Rarity.COMMON), @Printings.Printed(ex = Expansion.ONSLAUGHT, r = Rarity.COMMON)})
@ColorIdentity({Color.BLACK})
public final class BarrenMoor extends org.rnd.jmagic.cardTemplates.OnslaughtCyclingLand
{
	public BarrenMoor(GameState state)
	{
		super(state, "(B)");
	}
}
