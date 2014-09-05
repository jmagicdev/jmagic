package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.expansions.*;

@Name("Izzet Boilerworks")
@Types({Type.LAND})
@Printings({@Printings.Printed(ex = MagicTheGatheringCommander.class, r = Rarity.COMMON), @Printings.Printed(ex = Guildpact.class, r = Rarity.COMMON)})
@ColorIdentity({Color.BLUE, Color.RED})
public final class IzzetBoilerworks extends org.rnd.jmagic.cardTemplates.RavnicaBounceLand
{
	public IzzetBoilerworks(GameState state)
	{
		super(state, 'U', 'R');
	}
}
