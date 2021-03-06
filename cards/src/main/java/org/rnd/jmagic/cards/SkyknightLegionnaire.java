package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;

@Name("Skyknight Legionnaire")
@Types({Type.CREATURE})
@SubTypes({SubType.HUMAN, SubType.KNIGHT})
@ManaCost("1RW")
@ColorIdentity({Color.WHITE, Color.RED})
public final class SkyknightLegionnaire extends Card
{
	public SkyknightLegionnaire(GameState state)
	{
		super(state);

		this.setPower(2);
		this.setToughness(2);

		// Flying, haste
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Flying(state));
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Haste(state));
	}
}
