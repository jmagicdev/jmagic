package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;

@Name("Drudge Skeletons")
@Types({Type.CREATURE})
@SubTypes({SubType.SKELETON})
@ManaCost("1B")
@ColorIdentity({Color.BLACK})
public final class DrudgeSkeletons extends Card
{
	public DrudgeSkeletons(GameState state)
	{
		super(state);

		this.setPower(1);
		this.setToughness(1);

		this.addAbility(new org.rnd.jmagic.abilities.Regenerate.Final(state, "(B)", this.getName()));
	}
}
