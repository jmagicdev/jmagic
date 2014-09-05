package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.expansions.*;

@Name("Vastwood Gorger")
@Types({Type.CREATURE})
@SubTypes({SubType.WURM})
@ManaCost("5G")
@Printings({@Printings.Printed(ex = Magic2013.class, r = Rarity.COMMON), @Printings.Printed(ex = Magic2012.class, r = Rarity.COMMON), @Printings.Printed(ex = Zendikar.class, r = Rarity.COMMON)})
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
