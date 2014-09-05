package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.expansions.*;

@Name("Steadfast Guard")
@Types({Type.CREATURE})
@SubTypes({SubType.HUMAN, SubType.REBEL})
@ManaCost("WW")
@Printings({@Printings.Printed(ex = TenthEdition.class, r = Rarity.COMMON), @Printings.Printed(ex = MercadianMasques.class, r = Rarity.COMMON)})
@ColorIdentity({Color.WHITE})
public final class SteadfastGuard extends Card
{
	public SteadfastGuard(GameState state)
	{
		super(state);

		this.setPower(2);
		this.setToughness(2);

		this.addAbility(new org.rnd.jmagic.abilities.keywords.Vigilance(state));
	}
}
