package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Shieldmate's Blessing")
@Types({Type.INSTANT})
@ManaCost("W")
@ColorIdentity({Color.WHITE})
public final class ShieldmatesBlessing extends Card
{
	public ShieldmatesBlessing(GameState state)
	{
		super(state);

		Target target = this.addTarget(CREATURES_AND_PLAYERS, "target creature or player");

		EventFactory factory = new EventFactory(EventType.CREATE_FLOATING_CONTINUOUS_EFFECT, "Prevent the next 3 damage that would be dealt to target creature or player this turn.");
		factory.parameters.put(EventType.Parameter.CAUSE, This.instance());
		factory.parameters.put(EventType.Parameter.PREVENT, Identity.instance(targetedBy(target), 3));
		this.addEffect(factory);
	}
}
