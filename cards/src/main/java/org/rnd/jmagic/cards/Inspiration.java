package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Inspiration")
@Types({Type.INSTANT})
@ManaCost("3U")
@ColorIdentity({Color.BLUE})
public final class Inspiration extends Card
{
	public Inspiration(GameState state)
	{
		super(state);

		Target target = this.addTarget(Players.instance(), "target player");

		this.addEffect(drawCards(targetedBy(target), 2, "Target player draws two cards."));
	}
}
