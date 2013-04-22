package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;

@Name("Coastal Tower")
@Types({Type.LAND})
@Printings({@Printings.Printed(ex = Expansion.EIGHTH_EDITION, r = Rarity.UNCOMMON), @Printings.Printed(ex = Expansion.INVASION, r = Rarity.UNCOMMON)})
@ColorIdentity({Color.BLUE, Color.WHITE})
public final class CoastalTower extends Card
{
	public CoastalTower(GameState state)
	{
		super(state);

		// Coastal Tower enters the battlefield tapped.
		this.addAbility(new org.rnd.jmagic.abilities.EntersTheBattlefieldTapped(state, this.getName()));

		// (T): Add (W) or (U) to your mana pool.
		this.addAbility(new org.rnd.jmagic.abilities.TapForMana.Final(state, "(WU)"));
	}
}
