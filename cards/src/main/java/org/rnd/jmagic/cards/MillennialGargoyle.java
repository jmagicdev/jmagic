package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.expansions.*;

@Name("Millennial Gargoyle")
@Types({Type.CREATURE, Type.ARTIFACT})
@SubTypes({SubType.GARGOYLE})
@ManaCost("4")
@Printings({@Printings.Printed(ex = Gatecrash.class, r = Rarity.COMMON)})
@ColorIdentity({})
public final class MillennialGargoyle extends Card
{
	public MillennialGargoyle(GameState state)
	{
		super(state);

		this.setPower(2);
		this.setToughness(2);

		// Flying
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Flying(state));
	}
}
