package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;

@Name("Manor Skeleton")
@Types({Type.CREATURE})
@SubTypes({SubType.SKELETON})
@ManaCost("1B")
@Printings({@Printings.Printed(ex = Expansion.INNISTRAD, r = Rarity.COMMON)})
@ColorIdentity({Color.BLACK})
public final class ManorSkeleton extends Card
{
	public ManorSkeleton(GameState state)
	{
		super(state);

		this.setPower(1);
		this.setToughness(1);

		// Haste
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Haste(state));

		// (1)(B): Regenerate Manor Skeleton.
		this.addAbility(new org.rnd.jmagic.abilities.Regenerate.Final(state, "(1)(B)", this.getName()));
	}
}
