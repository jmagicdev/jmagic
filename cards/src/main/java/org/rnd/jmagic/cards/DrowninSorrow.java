package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Drown in Sorrow")
@Types({Type.SORCERY})
@ManaCost("1BB")
@Printings({@Printings.Printed(ex = Expansion.BORN_OF_THE_GODS, r = Rarity.UNCOMMON)})
@ColorIdentity({Color.BLACK})
public final class DrowninSorrow extends Card
{
	public DrowninSorrow(GameState state)
	{
		super(state);


		// All creatures get -2/-2 until end of turn. Scry 1. (Look at the top card of your library. You may put that card on the bottom of your library.)
		this.addEffect(ptChangeUntilEndOfTurn(CreaturePermanents.instance(), (-2), (-2), "All creatures get -2/-2 until end of turn."));
		this.addEffect(scry(1, "Scry 1."));
	}
}
