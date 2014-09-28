package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;

@Name("Nomad Outpost")
@Types({Type.LAND})
@ColorIdentity({Color.RED, Color.WHITE, Color.BLACK})
public final class NomadOutpost extends Card
{
	public NomadOutpost(GameState state)
	{
		super(state);

		// Nomad Outpost enters the battlefield tapped.
		this.addAbility(new org.rnd.jmagic.abilities.EntersTheBattlefieldTapped(state, this.getName()));

		// (T): Add (R), (W), or (B) to your mana pool.
		this.addAbility(new org.rnd.jmagic.abilities.TapForMana.Final(state, "(RWB)"));
	}
}
