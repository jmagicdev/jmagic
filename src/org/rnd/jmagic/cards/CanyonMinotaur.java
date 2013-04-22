package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;

@Name("Canyon Minotaur")
@Types({Type.CREATURE})
@SubTypes({SubType.WARRIOR, SubType.MINOTAUR})
@ManaCost("3R")
@Printings({@Printings.Printed(ex = Expansion.MAGIC_2013, r = Rarity.COMMON), @Printings.Printed(ex = Expansion.MAGIC_2011, r = Rarity.COMMON), @Printings.Printed(ex = Expansion.MAGIC_2010, r = Rarity.COMMON), @Printings.Printed(ex = Expansion.CONFLUX, r = Rarity.COMMON)})
@ColorIdentity({Color.RED})
public final class CanyonMinotaur extends Card
{
	public CanyonMinotaur(GameState state)
	{
		super(state);

		this.setPower(3);
		this.setToughness(3);
	}
}
