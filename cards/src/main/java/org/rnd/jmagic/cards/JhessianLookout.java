package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.expansions.*;

@Name("Jhessian Lookout")
@Types({Type.CREATURE})
@SubTypes({SubType.HUMAN, SubType.SCOUT})
@ManaCost("1U")
@Printings({@Printings.Printed(ex = ShardsOfAlara.class, r = Rarity.COMMON)})
@ColorIdentity({Color.BLUE})
public final class JhessianLookout extends Card
{
	public JhessianLookout(GameState state)
	{
		super(state);

		this.setPower(2);
		this.setToughness(1);
	}
}
