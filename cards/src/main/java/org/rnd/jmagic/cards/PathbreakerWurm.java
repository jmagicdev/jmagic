package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;

@Name("Pathbreaker Wurm")
@Types({Type.CREATURE})
@SubTypes({SubType.WURM})
@ManaCost("4GG")
@ColorIdentity({Color.GREEN})
public final class PathbreakerWurm extends Card
{
	public PathbreakerWurm(GameState state)
	{
		super(state);

		this.setPower(6);
		this.setToughness(4);

		// Soulbond (You may pair this creature with another unpaired creature
		// when either enters the battlefield. They remain paired for as long as
		// you control both of them.)
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Soulbond(state));

		// As long as Pathbreaker Wurm is paired with another creature, both
		// creatures have trample.
		this.addAbility(new org.rnd.jmagic.abilities.AbilityIfPaired.Final(state, "As long as Pathbreaker Wurm is paired with another creature, both creatures have trample.", org.rnd.jmagic.abilities.keywords.Trample.class));
	}
}
