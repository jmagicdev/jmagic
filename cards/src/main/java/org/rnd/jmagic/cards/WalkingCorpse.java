package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;

@Name("Walking Corpse")
@Types({Type.CREATURE})
@SubTypes({SubType.ZOMBIE})
@ManaCost("1B")
@ColorIdentity({Color.BLACK})
public final class WalkingCorpse extends Card
{
	public WalkingCorpse(GameState state)
	{
		super(state);

		this.setPower(2);
		this.setToughness(2);
	}
}
