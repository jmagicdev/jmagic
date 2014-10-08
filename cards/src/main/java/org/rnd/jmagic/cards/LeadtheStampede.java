package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Lead the Stampede")
@Types({Type.SORCERY})
@ManaCost("2G")
@ColorIdentity({Color.GREEN})
public final class LeadtheStampede extends Card
{
	public LeadtheStampede(GameState state)
	{
		super(state);

		// Look at the top five cards of your library. You may reveal any number
		// of creature cards from among them and put the revealed cards into
		// your hand. Put the rest on the bottom of your library in any order.

		this.addEffect(Sifter.start().look(5).take(5, HasType.instance(Type.CREATURE)).dumpToBottom().getEventFactory("Look at the top five cards of your library. You may reveal any number of creature cards from among them and put the revealed cards into your hand. Put the rest on the bottom of your library in any order."));
	}
}
