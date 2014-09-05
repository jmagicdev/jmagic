package org.rnd.jmagic.cards;

import org.rnd.jmagic.abilities.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.expansions.*;

@Name("Copperline Gorge")
@Types({Type.LAND})
@Printings({@Printings.Printed(ex = ScarsOfMirrodin.class, r = Rarity.RARE)})
@ColorIdentity({Color.GREEN, Color.RED})
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
