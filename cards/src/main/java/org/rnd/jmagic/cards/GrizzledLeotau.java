package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.expansions.*;

@Name("Grizzled Leotau")
@Types({Type.CREATURE})
@SubTypes({SubType.CAT})
@ManaCost("GW")
@Printings({@Printings.Printed(ex = AlaraReborn.class, r = Rarity.COMMON)})
@ColorIdentity({Color.WHITE, Color.GREEN})
public final class GrizzledLeotau extends Card
{
	public GrizzledLeotau(GameState state)
	{
		super(state);

		this.setPower(1);
		this.setToughness(5);
	}
}
