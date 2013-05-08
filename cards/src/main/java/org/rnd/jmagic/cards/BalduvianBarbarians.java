package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;

@Name("Balduvian Barbarians")
@Types({Type.CREATURE})
@SubTypes({SubType.BARBARIAN, SubType.HUMAN})
@ManaCost("1RR")
@Printings({@Printings.Printed(ex = Expansion.NINTH_EDITION, r = Rarity.COMMON), @Printings.Printed(ex = Expansion.EIGHTH_EDITION, r = Rarity.COMMON), @Printings.Printed(ex = Expansion.SEVENTH_EDITION, r = Rarity.COMMON), @Printings.Printed(ex = Expansion.SIXTH_EDITION, r = Rarity.COMMON), @Printings.Printed(ex = Expansion.ICE_AGE, r = Rarity.COMMON)})
@ColorIdentity({Color.RED})
public final class BalduvianBarbarians extends Card
{
	public BalduvianBarbarians(GameState state)
	{
		super(state);

		this.setPower(3);
		this.setToughness(2);
	}
}
