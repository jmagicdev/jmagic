package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;

@Name("Porcelain Legionnaire")
@Types({Type.CREATURE, Type.ARTIFACT})
@SubTypes({SubType.SOLDIER})
@ManaCost("2(W/P)")
@Printings({@Printings.Printed(ex = Expansion.NEW_PHYREXIA, r = Rarity.COMMON)})
@ColorIdentity({Color.WHITE})
public final class PorcelainLegionnaire extends Card
{
	public PorcelainLegionnaire(GameState state)
	{
		super(state);

		this.setPower(3);
		this.setToughness(1);

		// First strike
		this.addAbility(new org.rnd.jmagic.abilities.keywords.FirstStrike(state));
	}
}
