package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Thoughtflare")
@Types({Type.INSTANT})
@ManaCost("3UR")
@ColorIdentity({Color.BLUE, Color.RED})
public final class Thoughtflare extends Card
{
	public Thoughtflare(GameState state)
	{
		super(state);

		// Draw four cards, then discard two cards.
		this.addEffect(drawCards(You.instance(), 4, "Draw four cards,"));
		this.addEffect(discardCards(You.instance(), 2, "then discard two cards."));
	}
}
