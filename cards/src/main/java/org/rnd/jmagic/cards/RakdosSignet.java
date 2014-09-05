package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.expansions.*;

@Name("Rakdos Signet")
@ManaCost("2")
@Types({Type.ARTIFACT})
@Printings({@Printings.Printed(ex = MagicTheGatheringCommander.class, r = Rarity.COMMON), @Printings.Printed(ex = Dissension.class, r = Rarity.COMMON)})
@ColorIdentity({Color.BLACK, Color.RED})
public final class RakdosSignet extends org.rnd.jmagic.cardTemplates.Signet
{
	public RakdosSignet(GameState state)
	{
		super(state, 'B', 'R');
	}
}
