package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Armory of Iroas")
@Types({Type.ARTIFACT})
@SubTypes({SubType.EQUIPMENT})
@ManaCost("2")
@ColorIdentity({})
public final class ArmoryofIroas extends Card
{
	public static final class ArmoryofIroasAbility0 extends EventTriggeredAbility
	{
		public ArmoryofIroasAbility0(GameState state)
		{
			super(state, "Whenever equipped creature attacks, put a +1/+1 counter on it.");

			SetGenerator equipped = EquippedBy.instance(ABILITY_SOURCE_OF_THIS);
			this.addPattern(whenXAttacks(equipped));
			this.addEffect(putCounters(1, Counter.CounterType.PLUS_ONE_PLUS_ONE, equipped, "Put a +1/+1 counter on equipped creature."));
		}
	}

	public ArmoryofIroas(GameState state)
	{
		super(state);

		// Whenever equipped creature attacks, put a +1/+1 counter on it.
		this.addAbility(new ArmoryofIroasAbility0(state));

		// Equip (2)
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Equip(state, "(2)"));
	}
}
