package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.engine.patterns.*;

@Name("Warstorm Surge")
@Types({Type.ENCHANTMENT})
@ManaCost("5R")
@ColorIdentity({Color.RED})
public final class WarstormSurge extends Card
{
	public static final class WarstormSurgeAbility0 extends EventTriggeredAbility
	{
		public WarstormSurgeAbility0(GameState state)
		{
			super(state, "Whenever a creature enters the battlefield under your control, it deals damage equal to its power to target creature or player.");

			this.addPattern(new SimpleZoneChangePattern(null, Battlefield.instance(), CreaturePermanents.instance(), You.instance(), false));

			SetGenerator thatCreature = NewObjectOf.instance(TriggerZoneChange.instance(This.instance()));
			SetGenerator target = targetedBy(this.addTarget(CREATURES_AND_PLAYERS, "target creature or player"));

			EventFactory damage = new EventFactory(EventType.DEAL_DAMAGE_EVENLY, "That creature deals damage equal to its power to target creature or player.");
			damage.parameters.put(EventType.Parameter.SOURCE, thatCreature);
			damage.parameters.put(EventType.Parameter.NUMBER, PowerOf.instance(thatCreature));
			damage.parameters.put(EventType.Parameter.TAKER, target);
			this.addEffect(damage);
		}
	}

	public WarstormSurge(GameState state)
	{
		super(state);

		// Whenever a creature enters the battlefield under your control, it
		// deals damage equal to its power to target creature or player.
		this.addAbility(new WarstormSurgeAbility0(state));
	}
}
