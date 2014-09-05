package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.expansions.*;

@Name("Orzhov Basilica")
@Types({Type.LAND})
@Printings({@Printings.Printed(ex = MagicTheGatheringCommander.class, r = Rarity.COMMON), @Printings.Printed(ex = Guildpact.class, r = Rarity.COMMON)})
@ColorIdentity({Color.WHITE, Color.BLACK})
public final class OrzhovBasilica extends org.rnd.jmagic.cardTemplates.RavnicaBounceLand
{
	public OrzhovBasilica(GameState state)
	{
		super(state, 'W', 'B');
	}
}
