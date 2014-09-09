package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Stabbing Pain")
@Types({Type.INSTANT})
@ManaCost("B")
@ColorIdentity({Color.BLACK})
public final class StabbingPain extends Card
{
	public StabbingPain(GameState state)
	{
		super(state);

		SetGenerator target = targetedBy(this.addTarget(CreaturePermanents.instance(), "target creature"));
		// Target creature gets -1/-1 until end of turn.
		this.addEffect(ptChangeUntilEndOfTurn(target, -1, -1, "Target creature gets -1/-1 until end of turn."));
		// Tap that creature.
		this.addEffect(tap(target, "Tap that creature."));
	}
}
