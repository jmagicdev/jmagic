package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;

@Name("Wild Griffin")
@Types({Type.CREATURE})
@SubTypes({SubType.GRIFFIN})
@ManaCost("2W")
@Printings({@Printings.Printed(ex = Expansion.MAGIC_2011, r = Rarity.COMMON), @Printings.Printed(ex = Expansion.TENTH_EDITION, r = Rarity.COMMON), @Printings.Printed(ex = Expansion.STARTER_2000, r = Rarity.COMMON), @Printings.Printed(ex = Expansion.STARTER, r = Rarity.COMMON), @Printings.Printed(ex = Expansion.PORTAL_SECOND_AGE, r = Rarity.COMMON)})
@ColorIdentity({Color.WHITE})
public final class WildGriffin extends Card
{
	public WildGriffin(GameState state)
	{
		super(state);

		this.setPower(2);
		this.setToughness(2);

		this.addAbility(new org.rnd.jmagic.abilities.keywords.Flying(state));
	}
}
