package org.rnd.jmagic.cards;

import org.rnd.jmagic.abilities.*;
import org.rnd.jmagic.engine.*;

@Name("Copperline Gorge")
@Types({Type.LAND})
@ColorIdentity({Color.RED, Color.GREEN})
public final class CopperlineGorge extends Card
{
	public CopperlineGorge(GameState state)
	{
		super(state);

		// Copperline Gorge enters the battlefield tapped unless you control two
		// or fewer other lands.
		this.addAbility(new ScarsTappedLandAbility(state, "Copperline Gorge"));

		// (T): Add (R) or (G) to your mana pool.
		this.addAbility(new TapForMana.Final(state, "(RG)"));
	}
}
