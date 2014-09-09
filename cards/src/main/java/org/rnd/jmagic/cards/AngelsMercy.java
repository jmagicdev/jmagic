package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Angel's Mercy")
@Types({Type.INSTANT})
@ManaCost("2WW")
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
