package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.expansions.*;

@Name("Mox Sapphire")
@Types({Type.ARTIFACT})
@ManaCost("0")
@Printings({@Printings.Printed(ex = UnlimitedEdition.class, r = Rarity.RARE), @Printings.Printed(ex = LimitedEditionBeta.class, r = Rarity.RARE), @Printings.Printed(ex = LimitedEditionAlpha.class, r = Rarity.RARE)})
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
