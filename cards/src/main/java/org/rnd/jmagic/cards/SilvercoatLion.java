package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.expansions.*;

@Name("Silvercoat Lion")
@Types({Type.CREATURE})
@SubTypes({SubType.CAT})
@ManaCost("1W")
@Printings({@Printings.Printed(ex = Magic2013.class, r = Rarity.COMMON), @Printings.Printed(ex = Magic2011.class, r = Rarity.COMMON), @Printings.Printed(ex = Magic2010.class, r = Rarity.COMMON)})
@ColorIdentity({Color.WHITE})
public final class SilvercoatLion extends Card
{
	public SilvercoatLion(GameState state)
	{
		super(state);

		this.setPower(2);
		this.setToughness(2);
	}
}
