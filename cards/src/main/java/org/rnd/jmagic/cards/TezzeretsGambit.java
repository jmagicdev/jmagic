package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Tezzeret's Gambit")
@Types({Type.SORCERY})
@ManaCost("3(U/P)")
@Printings({@Printings.Printed(ex = Expansion.NEW_PHYREXIA, r = Rarity.UNCOMMON)})
@ColorIdentity({Color.BLUE})
public final class TezzeretsGambit extends Card
{
	public TezzeretsGambit(GameState state)
	{
		super(state);

		// Draw two cards, then proliferate. (You choose any number of
		// permanents and/or players with counters on them, then give each
		// another counter of a kind already there.)
		this.addEffect(drawCards(You.instance(), 2, "Draw two cards,"));
		this.addEffect(proliferate(You.instance(), "then proliferate."));
	}
}
