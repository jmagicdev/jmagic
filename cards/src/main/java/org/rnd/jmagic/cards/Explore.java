package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Explore")
@Types({Type.SORCERY})
@ManaCost("1G")
@ColorIdentity({Color.GREEN})
public final class Explore extends Card
{
	public Explore(GameState state)
	{
		super(state);

		// You may play an additional land this turn.
		this.addEffect(playExtraLands(You.instance(), 1, "You may play an additional land this turn."));

		// Draw a card.
		this.addEffect(drawCards(You.instance(), 1, "\n\nDraw a card."));
	}
}
