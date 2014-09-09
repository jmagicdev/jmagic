package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;

@Name("Golgari Guildgate")
@Types({Type.LAND})
@SubTypes({SubType.GATE})
@ColorIdentity({Color.GREEN, Color.BLACK})
public final class GolgariGuildgate extends Card
{
	public GolgariGuildgate(GameState state)
	{
		super(state);

		// Golgari Guildgate enters the battlefield tapped.
		this.addAbility(new org.rnd.jmagic.abilities.EntersTheBattlefieldTapped(state, this.getName()));

		// (T): Add (B) or (G) to your mana pool.
		this.addAbility(new org.rnd.jmagic.abilities.TapForMana.Final(state, "(BG)"));
	}
}
