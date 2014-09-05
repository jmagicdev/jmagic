package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.expansions.*;

@Name("Spineless Thug")
@Types({Type.CREATURE})
@SubTypes({SubType.ZOMBIE, SubType.MERCENARY})
@ManaCost("1B")
@Printings({@Printings.Printed(ex = TenthEdition.class, r = Rarity.COMMON), @Printings.Printed(ex = NinthEdition.class, r = Rarity.COMMON), @Printings.Printed(ex = EighthEdition.class, r = Rarity.COMMON), @Printings.Printed(ex = SeventhEdition.class, r = Rarity.COMMON), @Printings.Printed(ex = Nemesis.class, r = Rarity.COMMON)})
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
