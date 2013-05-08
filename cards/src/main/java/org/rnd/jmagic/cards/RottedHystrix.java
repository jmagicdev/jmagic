package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;

@Name("Rotted Hystrix")
@Types({Type.CREATURE})
@SubTypes({SubType.BEAST})
@ManaCost("4G")
@Printings({@Printings.Printed(ex = Expansion.NEW_PHYREXIA, r = Rarity.COMMON)})
@ColorIdentity({Color.GREEN})
public final class RottedHystrix extends Card
{
	public RottedHystrix(GameState state)
	{
		super(state);

		this.setPower(3);
		this.setToughness(6);
	}
}
