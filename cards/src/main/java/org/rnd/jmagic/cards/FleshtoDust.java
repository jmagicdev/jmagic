package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Flesh to Dust")
@Types({Type.INSTANT})
@ManaCost("3BB")
@ColorIdentity({Color.BLACK})
public final class FleshtoDust extends Card
{
	public FleshtoDust(GameState state)
	{
		super(state);

		// Destroy target creature. It can't be regenerated.
		Target target = this.addTarget(CreaturePermanents.instance(), "target creature");
		this.addEffects(bury(this, targetedBy(target), "Destroy target creature. It can't be regenerated."));
	}
}
