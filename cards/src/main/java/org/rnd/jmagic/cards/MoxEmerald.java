package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;

@Name("Mox Emerald")
@Types({Type.ARTIFACT})
@ManaCost("0")
@Printings({@Printings.Printed(ex = Expansion.UNLIMITED, r = Rarity.RARE), @Printings.Printed(ex = Expansion.BETA, r = Rarity.RARE), @Printings.Printed(ex = Expansion.ALPHA, r = Rarity.RARE)})
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
