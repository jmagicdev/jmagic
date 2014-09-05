package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.expansions.*;

@Name("Leatherback Baloth")
@Types({Type.CREATURE})
@SubTypes({SubType.BEAST})
@ManaCost("GGG")
@Printings({@Printings.Printed(ex = Worldwake.class, r = Rarity.UNCOMMON)})
@ColorIdentity({Color.GREEN})
public final class LeatherbackBaloth extends Card
{
	public LeatherbackBaloth(GameState state)
	{
		super(state);

		this.setPower(4);
		this.setToughness(5);
	}
}
