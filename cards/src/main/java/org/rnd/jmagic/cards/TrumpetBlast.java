package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Trumpet Blast")
@Types({Type.INSTANT})
@ManaCost("2R")
@ColorIdentity({Color.RED})
public final class TrumpetBlast extends Card
{
	public TrumpetBlast(GameState state)
	{
		super(state);

		this.addEffect(ptChangeUntilEndOfTurn(Attacking.instance(), (+2), (+0), "Attacking creatures get +2/+0 until end of turn."));
	}
}
