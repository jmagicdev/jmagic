package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;

@Name("Goblin Piker")
@Types({Type.CREATURE})
@SubTypes({SubType.WARRIOR, SubType.GOBLIN})
@ManaCost("1R")
@Printings({@Printings.Printed(ex = Expansion.MAGIC_2012, r = Rarity.COMMON), @Printings.Printed(ex = Expansion.MAGIC_2011, r = Rarity.COMMON), @Printings.Printed(ex = Expansion.MAGIC_2010, r = Rarity.COMMON), @Printings.Printed(ex = Expansion.TENTH_EDITION, r = Rarity.COMMON), @Printings.Printed(ex = Expansion.NINTH_EDITION, r = Rarity.COMMON), @Printings.Printed(ex = Expansion.PORTAL_SECOND_AGE, r = Rarity.COMMON)})
@ColorIdentity({Color.RED})
public final class GoblinPiker extends Card
{
	public GoblinPiker(GameState state)
	{
		super(state);

		this.setPower(2);
		this.setToughness(1);
	}
}
