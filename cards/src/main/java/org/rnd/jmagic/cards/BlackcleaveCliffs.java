package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;

@Name("Blackcleave Cliffs")
@Types({Type.LAND})
@ColorIdentity({Color.BLACK, Color.RED})
public final class BlackcleaveCliffs extends Card
{
	public BlackcleaveCliffs(GameState state)
	{
		super(state);

		// Blackcleave Cliffs enters the battlefield tapped unless you control
		// two or fewer other lands.
		this.addAbility(new org.rnd.jmagic.abilities.ScarsTappedLandAbility(state, "Blackcleave Cliffs"));

		// (T): Add (B) or (R) to your mana pool.
		this.addAbility(new org.rnd.jmagic.abilities.TapForMana.Final(state, "(BR)"));
	}
}
