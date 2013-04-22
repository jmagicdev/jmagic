package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;

@Name("Mox Pearl")
@Types({Type.ARTIFACT})
@ManaCost("0")
@Printings({@Printings.Printed(ex = Expansion.UNLIMITED, r = Rarity.RARE), @Printings.Printed(ex = Expansion.BETA, r = Rarity.RARE), @Printings.Printed(ex = Expansion.ALPHA, r = Rarity.RARE)})
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
