package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;

@Name("Zombie Outlander")
@ManaCost("UB")
@Types({Type.CREATURE})
@SubTypes({SubType.ZOMBIE, SubType.SCOUT})
@ColorIdentity({Color.BLUE, Color.BLACK})
public final class ZombieOutlander extends org.rnd.jmagic.cardTemplates.Outlander
{
	public ZombieOutlander(GameState state)
	{
		super(state, Color.GREEN);
	}
}
