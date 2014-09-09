package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Hydrosurge")
@Types({Type.INSTANT})
@ManaCost("U")
@ColorIdentity({Color.BLUE})
public final class Hydrosurge extends Card
{
	public Hydrosurge(GameState state)
	{
		super(state);

		// Target creature gets -5/-0 until end of turn.
		Target target = this.addTarget(CreaturePermanents.instance(), "target creature");
		this.addEffect(ptChangeUntilEndOfTurn(targetedBy(target), (-5), (-0), "Target creature gets -5/-0 until end of turn."));
	}
}
