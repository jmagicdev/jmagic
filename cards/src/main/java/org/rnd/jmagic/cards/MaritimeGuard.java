package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;

@Name("Maritime Guard")
@Types({Type.CREATURE})
@SubTypes({SubType.MERFOLK, SubType.SOLDIER})
@ManaCost("1U")
@Printings({@Printings.Printed(ex = Expansion.MAGIC_2011, r = Rarity.COMMON)})
@ColorIdentity({Color.BLUE})
public final class MaritimeGuard extends Card
{
	public MaritimeGuard(GameState state)
	{
		super(state);

		this.setPower(1);
		this.setToughness(3);
	}
}
