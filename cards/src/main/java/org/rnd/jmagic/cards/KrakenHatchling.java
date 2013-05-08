package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;

@Name("Kraken Hatchling")
@Types({Type.CREATURE})
@SubTypes({SubType.KRAKEN})
@ManaCost("U")
@Printings({@Printings.Printed(ex = Expansion.MAGIC_2013, r = Rarity.COMMON), @Printings.Printed(ex = Expansion.ZENDIKAR, r = Rarity.COMMON)})
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
