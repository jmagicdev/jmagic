package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.expansions.*;

@Name("Runeclaw Bear")
@Types({Type.CREATURE})
@SubTypes({SubType.BEAR})
@ManaCost("1G")
@Printings({@Printings.Printed(ex = Magic2012.class, r = Rarity.COMMON), @Printings.Printed(ex = Magic2011.class, r = Rarity.COMMON), @Printings.Printed(ex = Magic2010.class, r = Rarity.COMMON)})
@ColorIdentity({Color.GREEN})
public final class RuneclawBear extends Card
{
	public RuneclawBear(GameState state)
	{
		super(state);

		this.setPower(2);
		this.setToughness(2);
	}
}
