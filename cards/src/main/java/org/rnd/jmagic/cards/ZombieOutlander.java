package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.expansions.*;

@Name("Zombie Outlander")
@ManaCost("UB")
@Types({Type.CREATURE})
@SubTypes({SubType.ZOMBIE, SubType.SCOUT})
@Printings({@Printings.Printed(ex = Conflux.class, r = Rarity.COMMON)})
@ColorIdentity({Color.BLUE, Color.BLACK})
public final class ZombieOutlander extends org.rnd.jmagic.cardTemplates.Outlander
{
	public ZombieOutlander(GameState state)
	{
		super(state, Color.GREEN);
	}
}
