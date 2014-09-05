package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.expansions.*;

@Name("Rock Badger")
@Types({Type.CREATURE})
@SubTypes({SubType.BADGER, SubType.BEAST})
@ManaCost("4R")
@Printings({@Printings.Printed(ex = TenthEdition.class, r = Rarity.COMMON), @Printings.Printed(ex = MercadianMasques.class, r = Rarity.UNCOMMON)})
@ColorIdentity({Color.RED})
public final class RockBadger extends Card
{
	public RockBadger(GameState state)
	{
		super(state);

		this.setPower(3);
		this.setToughness(3);

		this.addAbility(new org.rnd.jmagic.abilities.keywords.Landwalk.Mountainwalk(state));
	}
}
