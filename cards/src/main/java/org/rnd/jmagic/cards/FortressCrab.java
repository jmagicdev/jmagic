package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.expansions.*;

@Name("Fortress Crab")
@Types({Type.CREATURE})
@SubTypes({SubType.CRAB})
@ManaCost("3U")
@Printings({@Printings.Printed(ex = Innistrad.class, r = Rarity.COMMON)})
@ColorIdentity({Color.BLUE})
public final class FortressCrab extends Card
{
	public FortressCrab(GameState state)
	{
		super(state);

		this.setPower(1);
		this.setToughness(6);
	}
}
