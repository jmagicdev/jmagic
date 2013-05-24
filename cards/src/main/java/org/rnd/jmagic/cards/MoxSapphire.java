package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;

@Name("Mox Sapphire")
@Types({Type.ARTIFACT})
@ManaCost("0")
@Printings({@Printings.Printed(ex = Expansion.UNLIMITED, r = Rarity.RARE), @Printings.Printed(ex = Expansion.BETA, r = Rarity.RARE), @Printings.Printed(ex = Expansion.ALPHA, r = Rarity.RARE)})
@ColorIdentity({Color.BLUE})
public final class MoxSapphire extends Card
{
	public MoxSapphire(GameState state)
	{
		super(state);

		// (T): Add (U) to your mana pool.
		this.addAbility(new org.rnd.jmagic.abilities.TapForU(state));
	}
}
