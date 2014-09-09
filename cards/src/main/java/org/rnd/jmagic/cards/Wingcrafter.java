package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;

@Name("Wingcrafter")
@Types({Type.CREATURE})
@SubTypes({SubType.WIZARD, SubType.HUMAN})
@ManaCost("U")
@ColorIdentity({Color.BLUE})
public final class Wingcrafter extends Card
{
	public Wingcrafter(GameState state)
	{
		super(state);

		this.setPower(1);
		this.setToughness(1);

		// Soulbond (You may pair this creature with another unpaired creature
		// when either enters the battlefield. They remain paired for as long as
		// you control both of them.)
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Soulbond(state));

		// As long as Wingcrafter is paired with another creature, both
		// creatures have flying.
		this.addAbility(new org.rnd.jmagic.abilities.AbilityIfPaired.Final(state, "As long as Wingcrafter is paired with another creature, both creatures have flying.", org.rnd.jmagic.abilities.keywords.Flying.class));
	}
}
