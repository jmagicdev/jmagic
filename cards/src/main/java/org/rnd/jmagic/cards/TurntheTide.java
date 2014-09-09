package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Turn the Tide")
@Types({Type.INSTANT})
@ManaCost("1U")
@ColorIdentity({Color.BLUE})
public final class TurntheTide extends Card
{
	public TurntheTide(GameState state)
	{
		super(state);

		// Creatures your opponents control get -2/-0 until end of turn.
		this.addEffect(ptChangeUntilEndOfTurn(Intersect.instance(CreaturePermanents.instance(), ControlledBy.instance(OpponentsOf.instance(You.instance()))), -2, -0, "Creatures your opponents control get -2/-0 until end of turn."));
	}
}
