package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.expansions.*;

@Name("Siege Mastodon")
@Types({Type.CREATURE})
@SubTypes({SubType.ELEPHANT})
@ManaCost("4W")
@Printings({@Printings.Printed(ex = Magic2012.class, r = Rarity.COMMON), @Printings.Printed(ex = Magic2011.class, r = Rarity.COMMON), @Printings.Printed(ex = Magic2010.class, r = Rarity.COMMON)})
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
