package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Altar's Reap")
@Types({Type.INSTANT})
@ManaCost("1B")
@ColorIdentity({Color.BLACK})
public final class AltarsReap extends Card
{
	public AltarsReap(GameState state)
	{
		super(state);

		// As an additional cost to cast Altar's Reap, sacrifice a creature.
		this.addCost(sacrifice(You.instance(), 1, HasType.instance(Type.CREATURE), "sacrifice a creature"));

		// Draw two cards.
		this.addEffect(drawCards(You.instance(), 2, "Draw two cards."));
	}
}
