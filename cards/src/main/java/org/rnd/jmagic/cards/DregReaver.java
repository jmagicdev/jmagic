package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.expansions.*;

@Name("Dreg Reaver")
@Types({Type.CREATURE})
@SubTypes({SubType.BEAST, SubType.ZOMBIE})
@ManaCost("4B")
@Printings({@Printings.Printed(ex = ShardsOfAlara.class, r = Rarity.COMMON)})
@ColorIdentity({Color.BLACK})
public final class DregReaver extends Card
{
	public DregReaver(GameState state)
	{
		super(state);

		this.setPower(4);
		this.setToughness(3);
	}
}
