package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;

@Name("Benalish Cavalry")
@Types({Type.CREATURE})
@SubTypes({SubType.HUMAN, SubType.KNIGHT})
@ManaCost("1W")
@Printings({@Printings.Printed(ex = Expansion.TIME_SPIRAL, r = Rarity.COMMON)})
@ColorIdentity({Color.WHITE})
public final class BenalishCavalry extends Card
{
	public BenalishCavalry(GameState state)
	{
		super(state);

		this.setPower(2);
		this.setToughness(2);

		this.addAbility(new org.rnd.jmagic.abilities.keywords.Flanking(state));
	}
}
