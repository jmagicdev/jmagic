package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;

@Name("Reach Through Mists")
@Types({Type.INSTANT})
@SubTypes({SubType.ARCANE})
@ManaCost("U")
@ColorIdentity({Color.BLUE})
public final class ReachThroughMists extends Card
{
	public ReachThroughMists(GameState state)
	{
		super(state);

		// Draw a card.
		this.addEffect(drawACard());
	}
}
