package org.rnd.jmagic.cards;

import org.rnd.jmagic.abilities.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.expansions.*;

@Name("Razorverge Thicket")
@Types({Type.LAND})
@Printings({@Printings.Printed(ex = ScarsOfMirrodin.class, r = Rarity.RARE)})
@ColorIdentity({Color.WHITE, Color.GREEN})
public final class RazorvergeThicket extends Card
{
	public RazorvergeThicket(GameState state)
	{
		super(state);

		// Razorverge Thicket enters the battlefield tapped unless you control
		// two or fewer other lands.
		this.addAbility(new ScarsTappedLandAbility(state, "Razorverge Thicket"));

		// (T): Add (G) or (W) to your mana pool.
		this.addAbility(new TapForMana.Final(state, "(GW)"));
	}
}
