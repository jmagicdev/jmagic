package org.rnd.jmagic.cards;

import org.rnd.jmagic.abilities.*;
import org.rnd.jmagic.engine.*;

@Name("Seachrome Coast")
@Types({Type.LAND})
@ColorIdentity({Color.WHITE, Color.BLUE})
public final class SeachromeCoast extends Card
{
	public SeachromeCoast(GameState state)
	{
		super(state);

		// Seachrome Coast enters the battlefield tapped unless you control two
		// or fewer other lands.
		this.addAbility(new ScarsTappedLandAbility(state, "Seachrome Coast"));

		// (T): Add (W) or (U) to your mana pool.
		this.addAbility(new org.rnd.jmagic.abilities.TapForMana.Final(state, "(WU)"));
	}
}
