package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Wild Guess")
@Types({Type.SORCERY})
@ManaCost("RR")
@Printings({@Printings.Printed(ex = Expansion.MAGIC_2013, r = Rarity.COMMON)})
@ColorIdentity({Color.RED})
public final class WildGuess extends Card
{
	public WildGuess(GameState state)
	{
		super(state);

		// As an additional cost to cast Wild Guess, discard a card.
		EventFactory cost = discardCards(You.instance(), 1, "discard a card");
		this.addCost(cost);

		// Draw two cards.
		this.addEffect(drawCards(You.instance(), 2, "Draw two cards."));
	}
}
