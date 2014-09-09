package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Sift")
@Types({Type.SORCERY})
@ManaCost("3U")
@ColorIdentity({Color.BLUE})
public final class Sift extends Card
{
	public Sift(GameState state)
	{
		super(state);

		this.addEffect(drawCards(You.instance(), 3, "Draw three cards,"));
		this.addEffect(discardCards(You.instance(), 1, "then discard a card."));
	}
}
