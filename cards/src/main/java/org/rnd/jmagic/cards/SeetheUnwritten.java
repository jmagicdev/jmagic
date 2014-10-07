package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("See the Unwritten")
@Types({Type.SORCERY})
@ManaCost("4GG")
@ColorIdentity({Color.GREEN})
public final class SeetheUnwritten extends Card
{
	public SeetheUnwritten(GameState state)
	{
		super(state);

		// Reveal the top eight cards of your library. You may put a creature
		// card from among them onto the battlefield. Put the rest into your
		// graveyard.

		// Ferocious \u2014 If you control a creature with power 4 or greater,
		// you may put two creature cards onto the battlefield instead of one.

		SetGenerator number = IfThenElse.instance(Ferocious.instance(), Between.instance(0, 2), Between.instance(0, 1));
		this.addEffect(Sifter.start().reveal(8).drop(number, HasType.instance(Type.CREATURE)).dumpToGraveyard().getEventFactory("Reveal the top eight cards of your library. You may put a creature card from among them onto the battlefield. Put the rest into your graveyard.\n\nFerocious \u2014 If you control a creature with power 4 or greater, you may put two creature cards onto the battlefield instead of one."));
	}
}
