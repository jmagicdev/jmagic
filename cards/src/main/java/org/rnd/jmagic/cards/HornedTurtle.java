package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;

@Name("Horned Turtle")
@Types({Type.CREATURE})
@SubTypes({SubType.TURTLE})
@ManaCost("2U")
@Printings({@Printings.Printed(ex = Expansion.MAGIC_2010, r = Rarity.COMMON), @Printings.Printed(ex = Expansion.NINTH_EDITION, r = Rarity.COMMON), @Printings.Printed(ex = Expansion.EIGHTH_EDITION, r = Rarity.COMMON), @Printings.Printed(ex = Expansion.SEVENTH_EDITION, r = Rarity.COMMON), @Printings.Printed(ex = Expansion.SIXTH_EDITION, r = Rarity.COMMON), @Printings.Printed(ex = Expansion.TEMPEST, r = Rarity.COMMON), @Printings.Printed(ex = Expansion.PORTAL, r = Rarity.COMMON)})
@ColorIdentity({Color.BLUE})
public final class HornedTurtle extends Card
{
	public HornedTurtle(GameState state)
	{
		super(state);

		this.setPower(1);
		this.setToughness(4);
	}
}
