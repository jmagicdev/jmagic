package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.expansions.*;

@Name("Elvish Warrior")
@Types({Type.CREATURE})
@SubTypes({SubType.WARRIOR, SubType.ELF})
@ManaCost("GG")
@Printings({@Printings.Printed(ex = Morningtide.class, r = Rarity.COMMON), @Printings.Printed(ex = NinthEdition.class, r = Rarity.COMMON), @Printings.Printed(ex = Onslaught.class, r = Rarity.COMMON)})
@ColorIdentity({Color.GREEN})
public final class ElvishWarrior extends Card
{
	public ElvishWarrior(GameState state)
	{
		super(state);

		this.setPower(2);
		this.setToughness(3);
	}
}
