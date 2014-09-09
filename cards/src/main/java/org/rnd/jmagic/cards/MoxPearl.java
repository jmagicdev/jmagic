package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;

@Name("Mox Pearl")
@Types({Type.ARTIFACT})
@ManaCost("0")
@ColorIdentity({Color.WHITE})
public final class MoxPearl extends Card
{
	public MoxPearl(GameState state)
	{
		super(state);

		// (T): Add (W) to your mana pool.
		this.addAbility(new org.rnd.jmagic.abilities.TapForW(state));
	}
}
