package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.expansions.*;

@Name("Kraken Hatchling")
@Types({Type.CREATURE})
@SubTypes({SubType.KRAKEN})
@ManaCost("U")
@Printings({@Printings.Printed(ex = Magic2013.class, r = Rarity.COMMON), @Printings.Printed(ex = Zendikar.class, r = Rarity.COMMON)})
@ColorIdentity({Color.BLUE})
public final class KrakenHatchling extends Card
{
	public KrakenHatchling(GameState state)
	{
		super(state);

		this.setPower(0);
		this.setToughness(4);
	}
}
