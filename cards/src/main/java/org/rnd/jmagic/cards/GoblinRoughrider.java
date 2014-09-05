package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.expansions.*;

@Name("Goblin Roughrider")
@Types({Type.CREATURE})
@SubTypes({SubType.KNIGHT, SubType.GOBLIN})
@ManaCost("2R")
@Printings({@Printings.Printed(ex = Worldwake.class, r = Rarity.COMMON)})
@ColorIdentity({Color.RED})
public final class GoblinRoughrider extends Card
{
	public GoblinRoughrider(GameState state)
	{
		super(state);

		this.setPower(3);
		this.setToughness(2);
	}
}
