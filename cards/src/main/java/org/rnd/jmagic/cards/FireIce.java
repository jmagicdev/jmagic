package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.expansions.*;

@Name("Fire // Ice")
@Types({Type.INSTANT})
@Printings({@Printings.Printed(ex = Apocalypse.class, r = Rarity.UNCOMMON), @Printings.Printed(ex = MagicTheGatheringCommander.class, r = Rarity.UNCOMMON)})
@ColorIdentity({Color.BLUE, Color.RED})
public final class FireIce extends SplitCard
{
	public FireIce(GameState state)
	{
		super(state, Fire.class, Ice.class);
	}
}
