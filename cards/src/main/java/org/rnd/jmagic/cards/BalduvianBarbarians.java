package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.expansions.*;

@Name("Balduvian Barbarians")
@Types({Type.CREATURE})
@SubTypes({SubType.BARBARIAN, SubType.HUMAN})
@ManaCost("1RR")
@Printings({@Printings.Printed(ex = NinthEdition.class, r = Rarity.COMMON), @Printings.Printed(ex = EighthEdition.class, r = Rarity.COMMON), @Printings.Printed(ex = SeventhEdition.class, r = Rarity.COMMON), @Printings.Printed(ex = ClassicSixthEdition.class, r = Rarity.COMMON), @Printings.Printed(ex = IceAge.class, r = Rarity.COMMON)})
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
