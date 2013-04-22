package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;

@Name("Vastwood Gorger")
@Types({Type.CREATURE})
@SubTypes({SubType.WURM})
@ManaCost("5G")
@Printings({@Printings.Printed(ex = Expansion.MAGIC_2013, r = Rarity.COMMON), @Printings.Printed(ex = Expansion.MAGIC_2012, r = Rarity.COMMON), @Printings.Printed(ex = Expansion.ZENDIKAR, r = Rarity.COMMON)})
@ColorIdentity({Color.GREEN})
public final class VastwoodGorger extends Card
{
	public VastwoodGorger(GameState state)
	{
		super(state);

		this.setPower(5);
		this.setToughness(6);
	}
}
