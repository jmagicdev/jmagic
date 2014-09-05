package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.expansions.*;

@Name("Simic Signet")
@ManaCost("2")
@Types({Type.ARTIFACT})
@Printings({@Printings.Printed(ex = MagicTheGatheringCommander.class, r = Rarity.COMMON), @Printings.Printed(ex = Dissension.class, r = Rarity.COMMON)})
@ColorIdentity({Color.BLUE, Color.GREEN})
public final class SimicSignet extends org.rnd.jmagic.cardTemplates.Signet
{
	public SimicSignet(GameState state)
	{
		super(state, 'G', 'U');
	}
}
