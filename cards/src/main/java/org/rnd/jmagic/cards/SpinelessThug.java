package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;

@Name("Spineless Thug")
@Types({Type.CREATURE})
@SubTypes({SubType.ZOMBIE, SubType.MERCENARY})
@ManaCost("1B")
@Printings({@Printings.Printed(ex = Expansion.TENTH_EDITION, r = Rarity.COMMON), @Printings.Printed(ex = Expansion.NINTH_EDITION, r = Rarity.COMMON), @Printings.Printed(ex = Expansion.EIGHTH_EDITION, r = Rarity.COMMON), @Printings.Printed(ex = Expansion.SEVENTH_EDITION, r = Rarity.COMMON), @Printings.Printed(ex = Expansion.NEMESIS, r = Rarity.COMMON)})
@ColorIdentity({Color.BLACK})
public final class SpinelessThug extends Card
{
	public SpinelessThug(GameState state)
	{
		super(state);

		this.setPower(2);
		this.setToughness(2);

		this.addAbility(new org.rnd.jmagic.abilities.CantBlock(state, this.getName()));
	}
}
