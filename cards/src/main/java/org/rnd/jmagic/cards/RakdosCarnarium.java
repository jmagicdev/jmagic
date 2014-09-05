package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.expansions.*;

@Name("Rakdos Carnarium")
@Types({Type.LAND})
@Printings({@Printings.Printed(ex = MagicTheGatheringCommander.class, r = Rarity.COMMON), @Printings.Printed(ex = Dissension.class, r = Rarity.COMMON)})
@ColorIdentity({Color.BLACK, Color.RED})
public final class RakdosCarnarium extends org.rnd.jmagic.cardTemplates.RavnicaBounceLand
{
	public RakdosCarnarium(GameState state)
	{
		super(state, 'B', 'R');
	}
}
