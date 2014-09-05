package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.expansions.*;

@Name("Rhox Brute")
@Types({Type.CREATURE})
@SubTypes({SubType.WARRIOR, SubType.RHINO})
@ManaCost("2RG")
@Printings({@Printings.Printed(ex = AlaraReborn.class, r = Rarity.COMMON)})
@ColorIdentity({Color.GREEN, Color.RED})
public final class RhoxBrute extends Card
{
	public RhoxBrute(GameState state)
	{
		super(state);

		this.setPower(4);
		this.setToughness(4);
	}
}
