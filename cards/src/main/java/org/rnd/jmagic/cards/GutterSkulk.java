package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.expansions.*;

@Name("Gutter Skulk")
@Types({Type.CREATURE})
@SubTypes({SubType.RAT, SubType.ZOMBIE})
@ManaCost("1B")
@Printings({@Printings.Printed(ex = Gatecrash.class, r = Rarity.COMMON)})
@ColorIdentity({Color.BLACK})
public final class GutterSkulk extends Card
{
	public GutterSkulk(GameState state)
	{
		super(state);

		this.setPower(2);
		this.setToughness(2);
	}
}
