package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;

@Name("Mox Emerald")
@Types({Type.ARTIFACT})
@ManaCost("0")
@ColorIdentity({Color.GREEN})
public final class MoxEmerald extends Card
{
	public MoxEmerald(GameState state)
	{
		super(state);

		// (T): Add (G) to your mana pool.
		this.addAbility(new org.rnd.jmagic.abilities.TapForG(state));
	}
}
