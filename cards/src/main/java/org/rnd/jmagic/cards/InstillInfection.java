package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Instill Infection")
@Types({Type.INSTANT})
@ManaCost("3B")
@ColorIdentity({Color.BLACK})
public final class InstillInfection extends Card
{
	public InstillInfection(GameState state)
	{
		super(state);

		// Put a -1/-1 counter on target creature.
		SetGenerator target = targetedBy(this.addTarget(CreaturePermanents.instance(), "target creature"));
		this.addEffect(putCounters(1, Counter.CounterType.MINUS_ONE_MINUS_ONE, target, "Put a -1/-1 counter on target creature."));

		// Draw a card.
		this.addEffect(drawCards(You.instance(), 1, "\n\nDraw a card."));
	}
}
