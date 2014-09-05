package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.expansions.*;

@Name("Lagac Lizard")
@Types({Type.CREATURE})
@SubTypes({SubType.LIZARD})
@ManaCost("3R")
@Printings({@Printings.Printed(ex = RiseOfTheEldrazi.class, r = Rarity.COMMON)})
@ColorIdentity({Color.RED})
public final class LagacLizard extends Card
{
	public LagacLizard(GameState state)
	{
		super(state);

		this.setPower(3);
		this.setToughness(3);
	}
}
