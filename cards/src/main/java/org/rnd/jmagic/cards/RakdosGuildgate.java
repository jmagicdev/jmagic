package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;

@Name("Rakdos Guildgate")
@Types({Type.LAND})
@SubTypes({SubType.GATE})
@ColorIdentity({Color.BLACK, Color.RED})
public final class RakdosGuildgate extends Card
{
	public RakdosGuildgate(GameState state)
	{
		super(state);

		// Rakdos Guildgate enters the battlefield tapped.
		this.addAbility(new org.rnd.jmagic.abilities.EntersTheBattlefieldTapped(state, this.getName()));

		// (T): Add (B) or (R) to your mana pool.
		this.addAbility(new org.rnd.jmagic.abilities.TapForMana.Final(state, "(BR)"));
	}
}
