package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Tormenting Voice")
@Types({Type.SORCERY})
@ManaCost("1R")
@ColorIdentity({Color.RED})
public final class TormentingVoice extends Card
{
	public TormentingVoice(GameState state)
	{
		super(state);

		// As an additional cost to cast Tormenting Voice, discard a card.
		this.addCost(discardCards(You.instance(), 1, "discard a card"));

		// Draw two cards.
		this.addEffect(drawCards(You.instance(), 2, "Draw two cards."));
	}
}
