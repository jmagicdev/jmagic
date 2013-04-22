package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;

@Name("Silverblade Paladin")
@Types({Type.CREATURE})
@SubTypes({SubType.HUMAN, SubType.KNIGHT})
@ManaCost("1WW")
@Printings({@Printings.Printed(ex = Expansion.AVACYN_RESTORED, r = Rarity.RARE)})
@ColorIdentity({Color.WHITE})
public final class SilverbladePaladin extends Card
{
	public SilverbladePaladin(GameState state)
	{
		super(state);

		this.setPower(2);
		this.setToughness(2);

		// Soulbond (You may pair this creature with another unpaired creature
		// when either enters the battlefield. They remain paired for as long as
		// you control both of them.)
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Soulbond(state));

		// As long as Silverblade Paladin is paired with another creature, both
		// creatures have double strike.
		this.addAbility(new org.rnd.jmagic.abilities.AbilityIfPaired.Final(state, "As long as Silverblade Paladin is paired with another creature, both creatures have double strike.", org.rnd.jmagic.abilities.keywords.DoubleStrike.class));
	}
}
