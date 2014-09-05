package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.expansions.*;

@Name("Golgari Signet")
@ManaCost("2")
@Types({Type.ARTIFACT})
@Printings({@Printings.Printed(ex = MagicTheGatheringCommander.class, r = Rarity.COMMON), @Printings.Printed(ex = RavnicaCityOfGuilds.class, r = Rarity.COMMON)})
@ColorIdentity({Color.GREEN, Color.BLACK})
public final class GolgariSignet extends org.rnd.jmagic.cardTemplates.Signet
{
	public GolgariSignet(GameState state)
	{
		super(state, 'B', 'G');
	}
}
