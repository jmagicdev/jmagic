package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.expansions.*;

@Name("Izzet Signet")
@ManaCost("2")
@Types({Type.ARTIFACT})
@Printings({@Printings.Printed(ex = MagicTheGatheringCommander.class, r = Rarity.COMMON), @Printings.Printed(ex = Guildpact.class, r = Rarity.COMMON)})
@ColorIdentity({Color.BLUE, Color.RED})
public final class IzzetSignet extends org.rnd.jmagic.cardTemplates.Signet
{
	public IzzetSignet(GameState state)
	{
		super(state, 'U', 'R');
	}
}
