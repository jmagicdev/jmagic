package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.expansions.*;

@Name("Rushwood Dryad")
@Types({Type.CREATURE})
@SubTypes({SubType.DRYAD})
@ManaCost("1G")
@Printings({@Printings.Printed(ex = TenthEdition.class, r = Rarity.COMMON), @Printings.Printed(ex = EighthEdition.class, r = Rarity.COMMON), @Printings.Printed(ex = MercadianMasques.class, r = Rarity.COMMON)})
@ColorIdentity({Color.GREEN})
public final class RushwoodDryad extends Card
{
	public RushwoodDryad(GameState state)
	{
		super(state);

		this.setPower(2);
		this.setToughness(1);

		this.addAbility(new org.rnd.jmagic.abilities.keywords.Landwalk.Forestwalk(state));
	}
}
