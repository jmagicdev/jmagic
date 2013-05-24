package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;

@Name("Elvish Warrior")
@Types({Type.CREATURE})
@SubTypes({SubType.WARRIOR, SubType.ELF})
@ManaCost("GG")
@Printings({@Printings.Printed(ex = Expansion.MORNINGTIDE, r = Rarity.COMMON), @Printings.Printed(ex = Expansion.NINTH_EDITION, r = Rarity.COMMON), @Printings.Printed(ex = Expansion.ONSLAUGHT, r = Rarity.COMMON)})
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
