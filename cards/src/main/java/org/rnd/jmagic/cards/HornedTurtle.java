package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.expansions.*;

@Name("Horned Turtle")
@Types({Type.CREATURE})
@SubTypes({SubType.TURTLE})
@ManaCost("2U")
@Printings({@Printings.Printed(ex = Magic2010.class, r = Rarity.COMMON), @Printings.Printed(ex = NinthEdition.class, r = Rarity.COMMON), @Printings.Printed(ex = EighthEdition.class, r = Rarity.COMMON), @Printings.Printed(ex = SeventhEdition.class, r = Rarity.COMMON), @Printings.Printed(ex = ClassicSixthEdition.class, r = Rarity.COMMON), @Printings.Printed(ex = Tempest.class, r = Rarity.COMMON), @Printings.Printed(ex = Portal.class, r = Rarity.COMMON)})
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
