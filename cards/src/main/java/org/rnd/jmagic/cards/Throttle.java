package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Throttle")
@Types({Type.INSTANT})
@ManaCost("4B")
@ColorIdentity({Color.BLACK})
public final class Throttle extends Card
{
	public Throttle(GameState state)
	{
		super(state);

		// Target creature gets -4/-4 until end of turn.
		SetGenerator target = targetedBy(this.addTarget(CreaturePermanents.instance(), "target creature"));
		this.addEffect(ptChangeUntilEndOfTurn(target, -4, -4, "Target creature gets -4/-4 until end of turn."));
	}
}
