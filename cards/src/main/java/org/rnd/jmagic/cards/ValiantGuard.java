package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.expansions.*;

@Name("Valiant Guard")
@Types({Type.CREATURE})
@SubTypes({SubType.SOLDIER, SubType.HUMAN})
@ManaCost("W")
@Printings({@Printings.Printed(ex = Conflux.class, r = Rarity.COMMON)})
@ColorIdentity({Color.WHITE})
public final class ValiantGuard extends Card
{
	public ValiantGuard(GameState state)
	{
		super(state);

		this.setPower(0);
		this.setToughness(3);
	}
}
