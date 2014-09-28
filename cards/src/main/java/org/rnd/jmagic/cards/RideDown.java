package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Ride Down")
@Types({Type.INSTANT})
@ManaCost("RW")
@ColorIdentity({Color.RED, Color.WHITE})
public final class RideDown extends Card
{
	public RideDown(GameState state)
	{
		super(state);

		// Destroy target blocking creature.
		SetGenerator target = targetedBy(this.addTarget(Blocking.instance(), "target blocking creature"));
		this.addEffect(destroy(target, "Destroy target blocking creature."));

		// Creatures that were blocked by that creature this combat gain trample
		// until end of turn.
		SetGenerator blockedBy = BlockedBy.instance(target);
		this.addEffect(addAbilityUntilEndOfTurn(blockedBy, org.rnd.jmagic.abilities.keywords.Trample.class, "Creatures that were blocked by that creature this combat gain trample until end of turn."));
	}
}
