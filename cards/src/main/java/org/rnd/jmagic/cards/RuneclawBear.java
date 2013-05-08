package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;

@Name("Runeclaw Bear")
@Types({Type.CREATURE})
@SubTypes({SubType.BEAR})
@ManaCost("1G")
@Printings({@Printings.Printed(ex = Expansion.MAGIC_2012, r = Rarity.COMMON), @Printings.Printed(ex = Expansion.MAGIC_2011, r = Rarity.COMMON), @Printings.Printed(ex = Expansion.MAGIC_2010, r = Rarity.COMMON)})
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
