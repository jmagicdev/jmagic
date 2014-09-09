package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;

@Name("Falkenrath Marauders")
@Types({Type.CREATURE})
@SubTypes({SubType.VAMPIRE, SubType.WARRIOR})
@ManaCost("3RR")
@ColorIdentity({Color.RED})
public final class FalkenrathMarauders extends Card
{
	public static final class FalkenrathMaraudersAbility1 extends EventTriggeredAbility
	{
		public FalkenrathMaraudersAbility1(GameState state)
		{
			super(state, "Whenever Falkenrath Marauders deals combat damage to a player, put two +1/+1 counters on it.");
			this.addPattern(whenThisDealsCombatDamageToAPlayer());

			this.addEffect(putCounters(2, Counter.CounterType.PLUS_ONE_PLUS_ONE, ABILITY_SOURCE_OF_THIS, "Put two +1/+1 counters on it."));
		}
	}

	public FalkenrathMarauders(GameState state)
	{
		super(state);

		this.setPower(2);
		this.setToughness(2);

		// Flying, haste
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Flying(state));
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Haste(state));

		// Whenever Falkenrath Marauders deals combat damage to a player, put
		// two +1/+1 counters on it.
		this.addAbility(new FalkenrathMaraudersAbility1(state));
	}
}
