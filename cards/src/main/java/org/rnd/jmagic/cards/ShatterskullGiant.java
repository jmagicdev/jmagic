package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.expansions.*;

@Name("Shatterskull Giant")
@Types({Type.CREATURE})
@SubTypes({SubType.GIANT, SubType.WARRIOR})
@ManaCost("2RR")
@Printings({@Printings.Printed(ex = Zendikar.class, r = Rarity.COMMON)})
@ColorIdentity({Color.RED})
public final class ShatterskullGiant extends Card
{
	public ShatterskullGiant(GameState state)
	{
		super(state);

		this.setPower(4);
		this.setToughness(3);
	}
}
