package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Commune with Nature")
@Types({Type.SORCERY})
@ManaCost("G")
@ColorIdentity({Color.GREEN})
public final class CommunewithNature extends Card
{
	public CommunewithNature(GameState state)
	{
		super(state);

		this.addEffect(Sifter.start().look(5).take(1, HasType.instance(Type.CREATURE)).dumpToBottom().getEventFactory("Look at the top five cards of your library. You may reveal a creature card from among them and put it into your hand. Put the rest on the bottom of your library in any order."));
	}
}
