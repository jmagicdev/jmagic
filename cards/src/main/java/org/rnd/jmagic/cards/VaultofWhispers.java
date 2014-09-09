package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;

@Name("Vault of Whispers")
@Types({Type.LAND, Type.ARTIFACT})
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
