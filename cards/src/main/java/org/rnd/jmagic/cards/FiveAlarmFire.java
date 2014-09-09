package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;

@Name("Five-Alarm Fire")
@Types({Type.ENCHANTMENT})
@ManaCost("1RR")
@ColorIdentity({Color.RED})
public final class FiveAlarmFire extends Card
{
	public static final class FiveAlarmFireAbility0 extends EventTriggeredAbility
	{
		public FiveAlarmFireAbility0(GameState state)
		{
			super(state, "Whenever a creature you control deals combat damage, put a blaze counter on Five-Alarm Fire.");
			this.addPattern(whenDealsCombatDamage(CREATURES_YOU_CONTROL));
			this.addEffect(putCounters(1, Counter.CounterType.BLAZE, ABILITY_SOURCE_OF_THIS, "Put a blaze counter on Five-Alarm Fire."));
		}
	}

	public static final class FiveAlarmFireAbility1 extends ActivatedAbility
	{
		public FiveAlarmFireAbility1(GameState state)
		{
			super(state, "Remove five blaze counters from Five-Alarm Fire: Five-Alarm Fire deals 5 damage to target creature or player.");
			// Remove five blaze counters from Five-Alarm Fire
			this.addCost(removeCounters(5, Counter.CounterType.BLAZE, ABILITY_SOURCE_OF_THIS, "Remove five blaze counters from Five-Alarm Fire"));
			SetGenerator target = targetedBy(this.addTarget(CREATURES_AND_PLAYERS, "target creature or player"));
			this.addEffect(permanentDealDamage(5, target, "Five-Alarm Fire deals 5 damage to target creature or player."));
		}
	}

	public FiveAlarmFire(GameState state)
	{
		super(state);

		// Whenever a creature you control deals combat damage, put a blaze
		// counter on Five-Alarm Fire.
		this.addAbility(new FiveAlarmFireAbility0(state));

		// Remove five blaze counters from Five-Alarm Fire: Five-Alarm Fire
		// deals 5 damage to target creature or player.
		this.addAbility(new FiveAlarmFireAbility1(state));
	}
}
