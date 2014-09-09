package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Divine Verdict")
@Types({Type.INSTANT})
@ManaCost("3W")
@ColorIdentity({Color.WHITE})
public final class DivineVerdict extends Card
{
	public DivineVerdict(GameState state)
	{
		super(state);

		// Destroy target attacking or blocking creature.
		SetGenerator attackingOrBlocking = Union.instance(Attacking.instance(), Blocking.instance());
		Target target = this.addTarget(attackingOrBlocking, "target attacking or blocking creature");
		this.addEffect(destroy(targetedBy(target), "Destroy target attacking or blocking creature."));
	}
}
