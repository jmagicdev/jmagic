package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;

@Name("Rushwood Dryad")
@Types({Type.CREATURE})
@SubTypes({SubType.DRYAD})
@ManaCost("1G")
@Printings({@Printings.Printed(ex = Expansion.TENTH_EDITION, r = Rarity.COMMON), @Printings.Printed(ex = Expansion.EIGHTH_EDITION, r = Rarity.COMMON), @Printings.Printed(ex = Expansion.MERCADIAN_MASQUES, r = Rarity.COMMON)})
@ColorIdentity({Color.GREEN})
public final class RushwoodDryad extends Card
{
	public RushwoodDryad(GameState state)
	{
		super(state);

		this.setPower(2);
		this.setToughness(1);

		this.addAbility(new org.rnd.jmagic.abilities.keywords.Landwalk.Forestwalk(state));
	}
}
