package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.expansions.*;

@Name("Boros Signet")
@ManaCost("2")
@Types({Type.ARTIFACT})
@Printings({@Printings.Printed(ex = MagicTheGatheringCommander.class, r = Rarity.COMMON), @Printings.Printed(ex = RavnicaCityOfGuilds.class, r = Rarity.COMMON)})
@ColorIdentity({Color.WHITE, Color.RED})
public final class BorosSignet extends org.rnd.jmagic.cardTemplates.Signet
{
	public BorosSignet(GameState state)
	{
		super(state, 'R', 'W');
	}
}
