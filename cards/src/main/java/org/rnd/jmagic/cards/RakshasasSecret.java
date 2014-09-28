package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Rakshasa's Secret")
@Types({Type.SORCERY})
@ManaCost("2B")
@ColorIdentity({Color.BLACK})
public final class RakshasasSecret extends Card
{
	public RakshasasSecret(GameState state)
	{
		super(state);

		// Target opponent discards two cards.
		SetGenerator target = targetedBy(this.addTarget(OpponentsOf.instance(You.instance()), "target opponent"));
		this.addEffect(discardCards(target, 2, "Target opponent discards two cards."));

		// Put the top two cards of your library into your graveyard.
		this.addEffect(millCards(You.instance(), 2, "Put the top two cards of your library into your graveyard."));
	}
}
