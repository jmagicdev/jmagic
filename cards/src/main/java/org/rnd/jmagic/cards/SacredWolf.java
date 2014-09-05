package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.expansions.*;

@Name("Sacred Wolf")
@Types({Type.CREATURE})
@SubTypes({SubType.WOLF})
@ManaCost("2G")
@Printings({@Printings.Printed(ex = Magic2012.class, r = Rarity.COMMON), @Printings.Printed(ex = Magic2011.class, r = Rarity.COMMON)})
@ColorIdentity({Color.GREEN})
public final class SacredWolf extends Card
{
	public SacredWolf(GameState state)
	{
		super(state);

		this.setPower(3);
		this.setToughness(1);

		this.addAbility(new org.rnd.jmagic.abilities.keywords.Hexproof(state));
	}
}
