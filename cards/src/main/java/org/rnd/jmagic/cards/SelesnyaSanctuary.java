package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.expansions.*;

@Name("Selesnya Sanctuary")
@Types({Type.LAND})
@Printings({@Printings.Printed(ex = MagicTheGatheringCommander.class, r = Rarity.COMMON), @Printings.Printed(ex = RavnicaCityOfGuilds.class, r = Rarity.COMMON)})
@ColorIdentity({Color.WHITE, Color.GREEN})
public final class SelesnyaSanctuary extends org.rnd.jmagic.cardTemplates.RavnicaBounceLand
{
	public SelesnyaSanctuary(GameState state)
	{
		super(state, 'G', 'W');
	}
}
