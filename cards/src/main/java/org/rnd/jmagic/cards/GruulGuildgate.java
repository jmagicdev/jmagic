package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.expansions.*;

@Name("Gruul Guildgate")
@Types({Type.LAND})
@SubTypes({SubType.GATE})
@Printings({@Printings.Printed(ex = Gatecrash.class, r = Rarity.COMMON)})
@ColorIdentity({Color.GREEN, Color.RED})
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
