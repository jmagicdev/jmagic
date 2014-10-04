package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Stun")
@Types({Type.INSTANT})
@ManaCost("1R")
@ColorIdentity({Color.RED})
public final class Stun extends Card
{
	public Stun(GameState state)
	{
		super(state);

		Target target = this.addTarget(CreaturePermanents.instance(), "target creature");
		this.addEffect(cantBlockThisTurn(targetedBy(target), "Target creature can't block this turn."));
		this.addEffect(drawCards(You.instance(), 1, "\n\nDraw a card."));
	}
}
