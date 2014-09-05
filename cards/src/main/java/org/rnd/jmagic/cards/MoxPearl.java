package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.expansions.*;

@Name("Mox Pearl")
@Types({Type.ARTIFACT})
@ManaCost("0")
@Printings({@Printings.Printed(ex = UnlimitedEdition.class, r = Rarity.RARE), @Printings.Printed(ex = LimitedEditionBeta.class, r = Rarity.RARE), @Printings.Printed(ex = LimitedEditionAlpha.class, r = Rarity.RARE)})
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
