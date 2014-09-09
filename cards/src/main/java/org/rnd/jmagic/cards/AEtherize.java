package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("\u00C6therize")
@Types({Type.INSTANT})
@ManaCost("3U")
@ColorIdentity({Color.BLUE})
public final class AEtherize extends Card
{
	public AEtherize(GameState state)
	{
		super(state);

		// Return all attacking creatures to their owner's hand.
		this.addEffect(bounce(Attacking.instance(), "Return all attacking creatures to their owner's hand."));
	}
}
