package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;

@Name("Sigiled Paladin")
@Types({Type.CREATURE})
@SubTypes({SubType.HUMAN, SubType.KNIGHT})
@ManaCost("WW")
@ColorIdentity({Color.WHITE})
public final class SigiledPaladin extends Card
{
	public SigiledPaladin(GameState state)
	{
		super(state);

		this.setPower(2);
		this.setToughness(2);

		this.addAbility(new org.rnd.jmagic.abilities.keywords.FirstStrike(state));

		this.addAbility(new org.rnd.jmagic.abilities.keywords.Exalted(state));
	}
}
