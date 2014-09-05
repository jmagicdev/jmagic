package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.expansions.*;

@Name("Canyon Minotaur")
@Types({Type.CREATURE})
@SubTypes({SubType.WARRIOR, SubType.MINOTAUR})
@ManaCost("3R")
@Printings({@Printings.Printed(ex = Magic2013.class, r = Rarity.COMMON), @Printings.Printed(ex = Magic2011.class, r = Rarity.COMMON), @Printings.Printed(ex = Magic2010.class, r = Rarity.COMMON), @Printings.Printed(ex = Conflux.class, r = Rarity.COMMON)})
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
