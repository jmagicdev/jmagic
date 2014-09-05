package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.expansions.*;

@Name("Rotted Hystrix")
@Types({Type.CREATURE})
@SubTypes({SubType.BEAST})
@ManaCost("4G")
@Printings({@Printings.Printed(ex = NewPhyrexia.class, r = Rarity.COMMON)})
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
