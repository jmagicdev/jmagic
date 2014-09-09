package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Hysterical Blindness")
@Types({Type.INSTANT})
@ManaCost("2U")
@ColorIdentity({Color.BLUE})
public final class HystericalBlindness extends Card
{
	public HystericalBlindness(GameState state)
	{
		super(state);

		// Creatures your opponents control get -4/-0 until end of turn.
		this.addEffect(ptChangeUntilEndOfTurn(Intersect.instance(ControlledBy.instance(OpponentsOf.instance(You.instance())), CreaturePermanents.instance()), -4, -0, "Creatures your opponents control get -4/-0 until end of turn."));
	}
}
