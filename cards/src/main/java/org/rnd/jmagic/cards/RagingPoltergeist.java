package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.expansions.*;

@Name("Raging Poltergeist")
@Types({Type.CREATURE})
@SubTypes({SubType.SPIRIT})
@ManaCost("4R")
@Printings({@Printings.Printed(ex = AvacynRestored.class, r = Rarity.COMMON)})
@ColorIdentity({Color.RED})
public final class RagingPoltergeist extends Card
{
	public RagingPoltergeist(GameState state)
	{
		super(state);

		this.setPower(6);
		this.setToughness(1);
	}
}
