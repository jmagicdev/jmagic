package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.expansions.*;

@Name("Gruul Turf")
@Types({Type.LAND})
@Printings({@Printings.Printed(ex = MagicTheGatheringCommander.class, r = Rarity.COMMON), @Printings.Printed(ex = Guildpact.class, r = Rarity.COMMON)})
@ColorIdentity({Color.GREEN, Color.RED})
public final class GruulTurf extends org.rnd.jmagic.cardTemplates.RavnicaBounceLand
{
	public GruulTurf(GameState state)
	{
		super(state, 'R', 'G');
	}
}
