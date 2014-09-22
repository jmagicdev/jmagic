package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;

@Name("Gruul Guildgate")
@Types({Type.LAND})
@SubTypes({SubType.GATE})
@ColorIdentity({Color.RED, Color.GREEN})
public final class GruulGuildgate extends Card
{
	public GruulGuildgate(GameState state)
	{
		super(state);

		// Gruul Guildgate enters the battlefield tapped.
		this.addAbility(new org.rnd.jmagic.abilities.EntersTheBattlefieldTapped(state, this.getName()));

		// (T): Add (R) or (G) to your mana pool.
		this.addAbility(new org.rnd.jmagic.abilities.TapForMana.Final(state, "(RG)"));
	}
}
