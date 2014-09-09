package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;

@Name("Copper Myr")
@Types({Type.CREATURE, Type.ARTIFACT})
@SubTypes({SubType.MYR})
@ManaCost("2")
@ColorIdentity({Color.GREEN})
public final class CopperMyr extends Card
{
	public CopperMyr(GameState state)
	{
		super(state);

		this.setPower(1);
		this.setToughness(1);

		// (T): Add (G) to your mana pool.
		this.addAbility(new org.rnd.jmagic.abilities.TapForMana.Final(state, "(G)"));
	}
}
