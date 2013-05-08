package org.rnd.jmagic.cards;

import org.rnd.jmagic.abilities.*;
import org.rnd.jmagic.engine.*;

@Name("Darkslick Shores")
@Types({Type.LAND})
@Printings({@Printings.Printed(ex = Expansion.SCARS_OF_MIRRODIN, r = Rarity.RARE)})
@ColorIdentity({Color.BLUE, Color.BLACK})
public final class DarkslickShores extends Card
{
	public DarkslickShores(GameState state)
	{
		super(state);

		// Darkslick Shores enters the battlefield tapped unless you control two
		// or fewer other lands.
		this.addAbility(new ScarsTappedLandAbility(state, "Darkslick Shores"));

		// (T): Add (U) or (B) to your mana pool.
		this.addAbility(new TapForMana.Final(state, "(UB)"));
	}
}
