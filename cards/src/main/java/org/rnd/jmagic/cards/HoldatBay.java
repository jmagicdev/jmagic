package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Hold at Bay")
@Types({Type.INSTANT})
@ManaCost("1W")
@ColorIdentity({Color.WHITE})
public final class HoldatBay extends Card
{
	public HoldatBay(GameState state)
	{
		super(state);

		// Prevent the next 7 damage that would be dealt to target creature or
		// player this turn.
		Target target = this.addTarget(Union.instance(CreaturePermanents.instance(), Players.instance()), "target creature or player");

		EventFactory prevent = new EventFactory(EventType.CREATE_FLOATING_CONTINUOUS_EFFECT, "Prevent the next 7 damage that would be dealt to target creature or player this turn.");
		prevent.parameters.put(EventType.Parameter.CAUSE, This.instance());
		prevent.parameters.put(EventType.Parameter.PREVENT, Identity.instance(targetedBy(target), 7));
		this.addEffect(prevent);
	}
}
