package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;

@Name("Impulse")
@Types({Type.INSTANT})
@ManaCost("1U")
@ColorIdentity({Color.BLUE})
public final class Impulse extends Card
{
	public Impulse(GameState state)
	{
		super(state);

		this.addEffect(Sifter.start().look(4).take(1).dumpToBottom().getEventFactory("Look at the top four cards of your library. Put one of them into your hand and the rest on the bottom of your library in any order."));
	}
}
