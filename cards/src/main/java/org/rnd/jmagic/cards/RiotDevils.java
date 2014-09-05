package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.expansions.*;

@Name("Riot Devils")
@Types({Type.CREATURE})
@SubTypes({SubType.DEVIL})
@ManaCost("2R")
@Printings({@Printings.Printed(ex = Innistrad.class, r = Rarity.COMMON)})
@ColorIdentity({Color.RED})
public final class RiotDevils extends Card
{
	public RiotDevils(GameState state)
	{
		super(state);

		this.setPower(2);
		this.setToughness(3);
	}
}
