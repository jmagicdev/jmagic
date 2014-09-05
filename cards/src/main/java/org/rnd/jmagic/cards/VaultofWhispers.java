package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.expansions.*;

@Name("Vault of Whispers")
@Types({Type.LAND, Type.ARTIFACT})
@Printings({@Printings.Printed(ex = Mirrodin.class, r = Rarity.COMMON)})
@ColorIdentity({Color.BLACK})
public final class VaultofWhispers extends Card
{
	public VaultofWhispers(GameState state)
	{
		super(state);

		// (T): Add (B) to your mana pool.
		this.addAbility(new org.rnd.jmagic.abilities.TapForB(state));
	}
}
