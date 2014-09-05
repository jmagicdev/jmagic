package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.expansions.*;

@Name("Golgari Rot Farm")
@Types({Type.LAND})
@Printings({@Printings.Printed(ex = MagicTheGatheringCommander.class, r = Rarity.COMMON), @Printings.Printed(ex = RavnicaCityOfGuilds.class, r = Rarity.COMMON)})
@ColorIdentity({Color.GREEN, Color.BLACK})
public final class GolgariRotFarm extends org.rnd.jmagic.cardTemplates.RavnicaBounceLand
{
	public GolgariRotFarm(GameState state)
	{
		super(state, 'B', 'G');
	}
}
