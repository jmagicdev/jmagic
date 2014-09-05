package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.expansions.*;

@Name("Boros Garrison")
@Types({Type.LAND})
@Printings({@Printings.Printed(ex = MagicTheGatheringCommander.class, r = Rarity.COMMON), @Printings.Printed(ex = RavnicaCityOfGuilds.class, r = Rarity.COMMON)})
@ColorIdentity({Color.WHITE, Color.RED})
public final class BorosGarrison extends org.rnd.jmagic.cardTemplates.RavnicaBounceLand
{
	public BorosGarrison(GameState state)
	{
		super(state, 'R', 'W');
	}
}
