package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;

@Name("Sandsteppe Citadel")
@Types({Type.LAND})
@ColorIdentity({Color.WHITE, Color.BLACK, Color.GREEN})
public final class SandsteppeCitadel extends Card
{
	public SandsteppeCitadel(GameState state)
	{
		super(state);

		// Sandsteppe Citadel enters the battlefield tapped.
		this.addAbility(new org.rnd.jmagic.abilities.EntersTheBattlefieldTapped(state, this.getName()));

		// (T): Add (W), (B), or (G) to your mana pool.
		this.addAbility(new org.rnd.jmagic.abilities.TapForMana.Final(state, "(WBG)"));
	}
}
