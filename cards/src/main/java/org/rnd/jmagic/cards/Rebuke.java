package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Rebuke")
@Types({Type.INSTANT})
@ManaCost("2W")
@ColorIdentity({Color.WHITE})
public final class Rebuke extends Card
{
	public Rebuke(GameState state)
	{
		super(state);

		// Destroy target attacking creature.
		SetGenerator target = targetedBy(this.addTarget(Attacking.instance(), "target attacking creature"));
		this.addEffect(destroy(target, "Destroy target attacking creature."));
	}
}
