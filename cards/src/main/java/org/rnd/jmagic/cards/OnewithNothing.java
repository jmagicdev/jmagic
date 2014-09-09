package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("One with Nothing")
@Types({Type.INSTANT})
@ManaCost("B")
@ColorIdentity({Color.BLACK})
public final class OnewithNothing extends Card
{
	public OnewithNothing(GameState state)
	{
		super(state);

		this.addEffect(discardHand(You.instance(), "Discard your hand."));
	}
}
