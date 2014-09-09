package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Caress of Phyrexia")
@Types({Type.SORCERY})
@ManaCost("3BB")
@ColorIdentity({Color.BLACK})
public final class CaressofPhyrexia extends Card
{
	public CaressofPhyrexia(GameState state)
	{
		super(state);

		// Target player draws three cards, loses 3 life, and gets three poison
		// counters.
		SetGenerator target = targetedBy(this.addTarget(Players.instance(), "target player"));
		this.addEffect(drawCards(target, 3, "Target player draws 3 cards,"));
		this.addEffect(loseLife(target, 3, "loses 3 life,"));
		this.addEffect(putCounters(3, Counter.CounterType.POISON, target, "and gets three poison counters."));
	}
}
