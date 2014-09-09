package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;

@Name("Spectral Gateguards")
@Types({Type.CREATURE})
@SubTypes({SubType.SPIRIT, SubType.SOLDIER})
@ManaCost("4W")
@ColorIdentity({Color.WHITE})
public final class SpectralGateguards extends Card
{
	public SpectralGateguards(GameState state)
	{
		super(state);

		this.setPower(2);
		this.setToughness(5);

		// Soulbond (You may pair this creature with another unpaired creature
		// when either enters the battlefield. They remain paired for as long as
		// you control both of them.)
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Soulbond(state));

		// As long as Spectral Gateguards is paired with another creature, both
		// creatures have vigilance.
		this.addAbility(new org.rnd.jmagic.abilities.AbilityIfPaired.Final(state, "As long as Spectral Gateguards is paired with another creature, both creatures have vigilance.", org.rnd.jmagic.abilities.keywords.Vigilance.class));
	}
}
