package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Feed the Clan")
@Types({Type.INSTANT})
@ManaCost("1G")
@ColorIdentity({Color.GREEN})
public final class FeedtheClan extends Card
{
	public FeedtheClan(GameState state)
	{
		super(state);

		// You gain 5 life.

		// Ferocious \u2014 You gain 10 life instead if you control a creature
		// with power 4 or greater.

		SetGenerator amount = IfThenElse.instance(Ferocious.instance(), numberGenerator(10), numberGenerator(5));
		this.addEffect(gainLife(You.instance(), amount, "You gain 5 life.\n\nFerocious \u2014 You gain 10 life instead if you control a creature with power 4 or greater."));
	}
}
