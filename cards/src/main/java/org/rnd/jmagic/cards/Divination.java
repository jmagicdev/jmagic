package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Divination")
@Types({Type.SORCERY})
@ManaCost("2U")
@ColorIdentity({Color.BLUE})
public final class Divination extends Card
{
	public Divination(GameState state)
	{
		super(state);

		// Draw two cards.
		this.addEffect(drawCards(You.instance(), 2, "Draw two cards."));
	}
}
