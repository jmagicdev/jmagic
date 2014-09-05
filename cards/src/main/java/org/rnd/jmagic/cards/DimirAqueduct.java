package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.expansions.*;

@Name("Dimir Aqueduct")
@Types({Type.LAND})
@Printings({@Printings.Printed(ex = MagicTheGatheringCommander.class, r = Rarity.COMMON), @Printings.Printed(ex = RavnicaCityOfGuilds.class, r = Rarity.COMMON)})
@ColorIdentity({Color.BLUE, Color.BLACK})
public final class DimirAqueduct extends org.rnd.jmagic.cardTemplates.RavnicaBounceLand
{
	public DimirAqueduct(GameState state)
	{
		super(state, 'U', 'B');
	}
}
