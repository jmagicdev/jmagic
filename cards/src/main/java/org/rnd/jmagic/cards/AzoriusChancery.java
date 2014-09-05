package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.expansions.*;

@Name("Azorius Chancery")
@Types({Type.LAND})
@Printings({@Printings.Printed(ex = MagicTheGatheringCommander.class, r = Rarity.COMMON), @Printings.Printed(ex = Dissension.class, r = Rarity.COMMON)})
@ColorIdentity({Color.BLUE, Color.WHITE})
public final class AzoriusChancery extends org.rnd.jmagic.cardTemplates.RavnicaBounceLand
{
	public AzoriusChancery(GameState state)
	{
		super(state, 'W', 'U');
	}
}
