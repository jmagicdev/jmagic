package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;

@Name("Plover Knights")
@Types({Type.CREATURE})
@SubTypes({SubType.KITHKIN, SubType.KNIGHT})
@ManaCost("3WW")
@Printings({@Printings.Printed(ex = Expansion.LORWYN, r = Rarity.COMMON)})
@ColorIdentity({Color.WHITE})
public final class PloverKnights extends Card
{
	public PloverKnights(GameState state)
	{
		super(state);

		this.setPower(3);
		this.setToughness(3);

		// Flying, first strike
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Flying(state));
		this.addAbility(new org.rnd.jmagic.abilities.keywords.FirstStrike(state));
	}
}
