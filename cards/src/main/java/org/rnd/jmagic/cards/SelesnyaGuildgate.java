package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;

@Name("Selesnya Guildgate")
@Types({Type.LAND})
@SubTypes({SubType.GATE})
@ColorIdentity({Color.WHITE, Color.GREEN})
public final class SelesnyaGuildgate extends Card
{
	public SelesnyaGuildgate(GameState state)
	{
		super(state);

		// Selesnya Guildgate enters the battlefield tapped.
		this.addAbility(new org.rnd.jmagic.abilities.EntersTheBattlefieldTapped(state, this.getName()));

		// (T): Add (G) or (W) to your mana pool.
		this.addAbility(new org.rnd.jmagic.abilities.TapForMana.Final(state, "(GW)"));
	}
}
