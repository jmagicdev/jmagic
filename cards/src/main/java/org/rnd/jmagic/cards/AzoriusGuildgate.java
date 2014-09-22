package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;

@Name("Azorius Guildgate")
@Types({Type.LAND})
@SubTypes({SubType.GATE})
@ColorIdentity({Color.WHITE, Color.BLUE})
public final class AzoriusGuildgate extends Card
{
	public AzoriusGuildgate(GameState state)
	{
		super(state);

		// Azorius Guildgate enters the battlefield tapped.
		this.addAbility(new org.rnd.jmagic.abilities.EntersTheBattlefieldTapped(state, this.getName()));

		// (T): Add (W) or (U) to your mana pool.
		this.addAbility(new org.rnd.jmagic.abilities.TapForMana.Final(state, "(WU)"));
	}
}
