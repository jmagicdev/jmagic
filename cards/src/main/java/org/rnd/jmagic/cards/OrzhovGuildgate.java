package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.expansions.*;

@Name("Orzhov Guildgate")
@Types({Type.LAND})
@SubTypes({SubType.GATE})
@Printings({@Printings.Printed(ex = Gatecrash.class, r = Rarity.COMMON)})
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
