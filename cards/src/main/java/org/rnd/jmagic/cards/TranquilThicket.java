package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;

@Name("Tranquil Thicket")
@Types({Type.LAND})
@Printings({@Printings.Printed(ex = Expansion.COMMANDER, r = Rarity.COMMON), @Printings.Printed(ex = Expansion.ONSLAUGHT, r = Rarity.COMMON)})
@ColorIdentity({Color.GREEN})
public final class TranquilThicket extends org.rnd.jmagic.cardTemplates.OnslaughtCyclingLand
{
	public TranquilThicket(GameState state)
	{
		super(state, "(G)");
	}
}
