package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.expansions.*;

@Name("Angel's Mercy")
@Types({Type.INSTANT})
@ManaCost("2WW")
@Printings({@Printings.Printed(ex = Magic2013.class, r = Rarity.COMMON), @Printings.Printed(ex = AvacynRestored.class, r = Rarity.COMMON), @Printings.Printed(ex = Magic2012.class, r = Rarity.COMMON), @Printings.Printed(ex = Magic2010.class, r = Rarity.COMMON)})
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
