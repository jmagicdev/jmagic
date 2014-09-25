package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;

@Name("Frontier Bivouac")
@Types({Type.LAND})
@ColorIdentity({Color.RED, Color.BLUE, Color.GREEN})
public final class FrontierBivouac extends Card
{
	public FrontierBivouac(GameState state)
	{
		super(state);

		// Frontier Bivouac enters the battlefield tapped.
		this.addAbility(new org.rnd.jmagic.abilities.EntersTheBattlefieldTapped(state, this.getName()));

		// (T): Add (G), (U), or (R) to your mana pool.
		this.addAbility(new org.rnd.jmagic.abilities.TapForMana.Final(state, "(GUR)"));
	}
}
