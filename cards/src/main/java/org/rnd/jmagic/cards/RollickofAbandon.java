package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Rollick of Abandon")
@Types({Type.SORCERY})
@ManaCost("3RR")
@ColorIdentity({Color.RED})
public final class RollickofAbandon extends Card
{
	public RollickofAbandon(GameState state)
	{
		super(state);

		// All creatures get +2/-2 until end of turn.
		this.addEffect(ptChangeUntilEndOfTurn(CreaturePermanents.instance(), +2, -2, "All creatures get +2/-2 until end of turn."));
	}
}
