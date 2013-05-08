package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;

@Name("Tundra Wolves")
@Types({Type.CREATURE})
@SubTypes({SubType.WOLF})
@ManaCost("W")
@Printings({@Printings.Printed(ex = Expansion.TENTH_EDITION, r = Rarity.COMMON), @Printings.Printed(ex = Expansion.EIGHTH_EDITION, r = Rarity.COMMON), @Printings.Printed(ex = Expansion.SIXTH_EDITION, r = Rarity.COMMON), @Printings.Printed(ex = Expansion.FIFTH_EDITION, r = Rarity.COMMON), @Printings.Printed(ex = Expansion.FOURTH_EDITION, r = Rarity.COMMON), @Printings.Printed(ex = Expansion.LEGENDS, r = Rarity.COMMON)})
@ColorIdentity({Color.WHITE})
public final class TundraWolves extends Card
{
	public TundraWolves(GameState state)
	{
		super(state);

		this.setPower(1);
		this.setToughness(1);

		this.addAbility(new org.rnd.jmagic.abilities.keywords.FirstStrike(state));
	}
}
