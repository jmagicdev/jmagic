package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Death's Presence")
@Types({Type.ENCHANTMENT})
@ManaCost("5G")
@ColorIdentity({Color.GREEN})
public final class DeathsPresence extends Card
{
	public static final class DeathsPresenceAbility0 extends EventTriggeredAbility
	{
		public DeathsPresenceAbility0(GameState state)
		{
			super(state, "Whenever a creature you control dies, put X +1/+1 counters on target creature you control, where X is the power of the creature that died.");
			this.addPattern(whenXDies(CREATURES_YOU_CONTROL));

			SetGenerator target = targetedBy(this.addTarget(CREATURES_YOU_CONTROL, "target creature you control"));
			SetGenerator X = PowerOf.instance(OldObjectOf.instance(TriggerZoneChange.instance(This.instance())));
			this.addEffect(putCounters(X, Counter.CounterType.PLUS_ONE_PLUS_ONE, target, "Put X +1/+1 counters on target creature you control, where X is the power of the creature that died."));
		}
	}

	public DeathsPresence(GameState state)
	{
		super(state);

		// Whenever a creature you control dies, put X +1/+1 counters on target
		// creature you control, where X is the power of the creature that died.
		this.addAbility(new DeathsPresenceAbility0(state));
	}
}
