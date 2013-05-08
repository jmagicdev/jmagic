package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;

@Name("Mox Ruby")
@Types({Type.ARTIFACT})
@ManaCost("0")
@Printings({@Printings.Printed(ex = Expansion.UNLIMITED, r = Rarity.RARE), @Printings.Printed(ex = Expansion.BETA, r = Rarity.RARE), @Printings.Printed(ex = Expansion.ALPHA, r = Rarity.RARE)})
@ColorIdentity({Color.RED})
public final class MoxRuby extends Card
{
	public MoxRuby(GameState state)
	{
		super(state);

		// (T): Add (R) to your mana pool.
		this.addAbility(new org.rnd.jmagic.abilities.TapForR(state));
	}
}
