package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;

@Name("Stromkirk Patrol")
@Types({Type.CREATURE})
@SubTypes({SubType.SOLDIER, SubType.VAMPIRE})
@ManaCost("4B")
@ColorIdentity({Color.BLACK})
public final class StromkirkPatrol extends Card
{
	public static final class StromkirkPatrolAbility0 extends EventTriggeredAbility
	{
		public StromkirkPatrolAbility0(GameState state)
		{
			super(state, "Whenever Stromkirk Patrol deals combat damage to a player, put a +1/+1 counter on it.");
			this.addPattern(whenThisDealsCombatDamageToAPlayer());
			this.addEffect(putCounters(1, Counter.CounterType.PLUS_ONE_PLUS_ONE, ABILITY_SOURCE_OF_THIS, "Put a +1/+1 counter on it."));
		}
	}

	public StromkirkPatrol(GameState state)
	{
		super(state);

		this.setPower(4);
		this.setToughness(3);

		// Whenever Stromkirk Patrol deals combat damage to a player, put a
		// +1/+1 counter on it.
		this.addAbility(new StromkirkPatrolAbility0(state));
	}
}
