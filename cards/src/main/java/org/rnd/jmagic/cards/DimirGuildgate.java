package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;

@Name("Dimir Guildgate")
@Types({Type.LAND})
@SubTypes({SubType.GATE})
@ColorIdentity({Color.BLUE, Color.BLACK})
public final class DimirGuildgate extends Card
{
	public DimirGuildgate(GameState state)
	{
		super(state);

		// Dimir Guildgate enters the battlefield tapped.
		this.addAbility(new org.rnd.jmagic.abilities.EntersTheBattlefieldTapped(state, this.getName()));

		// (T): Add (U) or (B) to your mana pool.
		this.addAbility(new org.rnd.jmagic.abilities.TapForMana.Final(state, "(UB)"));
	}
}
