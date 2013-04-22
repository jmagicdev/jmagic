package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Angel's Mercy")
@Types({Type.INSTANT})
@ManaCost("2WW")
@Printings({@Printings.Printed(ex = Expansion.MAGIC_2013, r = Rarity.COMMON), @Printings.Printed(ex = Expansion.AVACYN_RESTORED, r = Rarity.COMMON), @Printings.Printed(ex = Expansion.MAGIC_2012, r = Rarity.COMMON), @Printings.Printed(ex = Expansion.MAGIC_2010, r = Rarity.COMMON)})

@ColorIdentity({Color.WHITE})
public final class AngelsMercy extends Card
{
	public AngelsMercy(GameState state)
	{
		super(state);

		// You gain 7 life.
		this.addEffect(gainLife(You.instance(), 7, "You gain 7 life."));
	}
}
