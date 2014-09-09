package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;

@Name("Incurable Ogre")
@Types({Type.CREATURE})
@SubTypes({SubType.MUTANT, SubType.OGRE})
@ManaCost("3R")
@ColorIdentity({Color.RED})
public final class IncurableOgre extends Card
{
	public IncurableOgre(GameState state)
	{
		super(state);

		this.setPower(5);
		this.setToughness(1);
	}
}
