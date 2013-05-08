package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;

@Name("Silvercoat Lion")
@Types({Type.CREATURE})
@SubTypes({SubType.CAT})
@ManaCost("1W")
@Printings({@Printings.Printed(ex = Expansion.MAGIC_2013, r = Rarity.COMMON), @Printings.Printed(ex = Expansion.MAGIC_2011, r = Rarity.COMMON), @Printings.Printed(ex = Expansion.MAGIC_2010, r = Rarity.COMMON)})
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
