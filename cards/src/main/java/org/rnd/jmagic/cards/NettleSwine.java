package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.expansions.*;

@Name("Nettle Swine")
@Types({Type.CREATURE})
@SubTypes({SubType.BOAR})
@ManaCost("3G")
@Printings({@Printings.Printed(ex = AvacynRestored.class, r = Rarity.COMMON)})
@ColorIdentity({Color.GREEN})
public final class NettleSwine extends Card
{
	public NettleSwine(GameState state)
	{
		super(state);

		this.setPower(4);
		this.setToughness(3);
	}
}
