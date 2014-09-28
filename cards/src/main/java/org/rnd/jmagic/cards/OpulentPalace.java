package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;

@Name("Opulent Palace")
@Types({Type.LAND})
@ColorIdentity({Color.BLUE, Color.BLACK, Color.GREEN})
public final class OpulentPalace extends Card
{
	public OpulentPalace(GameState state)
	{
		super(state);

		// Opulent Palace enters the battlefield tapped.
		this.addAbility(new org.rnd.jmagic.abilities.EntersTheBattlefieldTapped(state, this.getName()));

		// (T): Add (B), (G), or (U) to your mana pool.
		this.addAbility(new org.rnd.jmagic.abilities.TapForMana.Final(state, "(BGU)"));
	}
}
