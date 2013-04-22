package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;

@Name("Gruul Turf")
@Types({Type.LAND})
@Printings({@Printings.Printed(ex = Expansion.COMMANDER, r = Rarity.COMMON), @Printings.Printed(ex = Expansion.GUILDPACT, r = Rarity.COMMON)})
@ColorIdentity({Color.GREEN, Color.RED})
public final class GruulTurf extends org.rnd.jmagic.cardTemplates.RavnicaBounceLand
{
	public GruulTurf(GameState state)
	{
		super(state, 'R', 'G');
	}
}
