package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;

@Name("Orzhov Guildgate")
@Types({Type.LAND})
@SubTypes({SubType.GATE})
@ColorIdentity({Color.WHITE, Color.BLACK})
public final class OrzhovGuildgate extends Card
{
	public OrzhovGuildgate(GameState state)
	{
		super(state);

		// Orzhov Guildgate enters the battlefield tapped.
		this.addAbility(new org.rnd.jmagic.abilities.EntersTheBattlefieldTapped(state, this.getName()));

		// (T): Add (W) or (B) to your mana pool.
		this.addAbility(new org.rnd.jmagic.abilities.TapForMana.Final(state, "(WB)"));
	}
}
