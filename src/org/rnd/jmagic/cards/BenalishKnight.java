package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;

@Name("Benalish Knight")
@Types({Type.CREATURE})
@SubTypes({SubType.HUMAN, SubType.KNIGHT})
@ManaCost("2W")
@Printings({@Printings.Printed(ex = Expansion.TENTH_EDITION, r = Rarity.COMMON), @Printings.Printed(ex = Expansion.WEATHERLIGHT, r = Rarity.COMMON)})
@ColorIdentity({Color.WHITE})
public final class BenalishKnight extends Card
{
	public BenalishKnight(GameState state)
	{
		super(state);

		this.setPower(2);
		this.setToughness(2);

		this.addAbility(new org.rnd.jmagic.abilities.keywords.Flash(state));
		this.addAbility(new org.rnd.jmagic.abilities.keywords.FirstStrike(state));
	}
}
