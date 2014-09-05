package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.expansions.*;

@Name("Tenement Crasher")
@Types({Type.CREATURE})
@SubTypes({SubType.BEAST})
@ManaCost("5R")
@Printings({@Printings.Printed(ex = ReturnToRavnica.class, r = Rarity.COMMON)})
@ColorIdentity({Color.RED})
public final class TenementCrasher extends Card
{
	public TenementCrasher(GameState state)
	{
		super(state);

		this.setPower(5);
		this.setToughness(4);

		// Haste
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Haste(state));
	}
}
