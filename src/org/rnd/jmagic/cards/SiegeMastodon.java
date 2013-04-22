package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;

@Name("Siege Mastodon")
@Types({Type.CREATURE})
@SubTypes({SubType.ELEPHANT})
@ManaCost("4W")
@Printings({@Printings.Printed(ex = Expansion.MAGIC_2012, r = Rarity.COMMON), @Printings.Printed(ex = Expansion.MAGIC_2011, r = Rarity.COMMON), @Printings.Printed(ex = Expansion.MAGIC_2010, r = Rarity.COMMON)})
@ColorIdentity({Color.WHITE})
public final class SiegeMastodon extends Card
{
	public SiegeMastodon(GameState state)
	{
		super(state);

		this.setPower(3);
		this.setToughness(5);
	}
}
