package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.expansions.*;

@Name("Gruul Signet")
@ManaCost("2")
@Types({Type.ARTIFACT})
@Printings({@Printings.Printed(ex = MagicTheGatheringCommander.class, r = Rarity.COMMON), @Printings.Printed(ex = Guildpact.class, r = Rarity.COMMON)})
@ColorIdentity({Color.GREEN, Color.RED})
public final class GruulSignet extends org.rnd.jmagic.cardTemplates.Signet
{
	public GruulSignet(GameState state)
	{
		super(state, 'R', 'G');
	}
}
