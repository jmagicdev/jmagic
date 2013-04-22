package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;

@Name("Slith Predator")
@Types({Type.CREATURE})
@SubTypes({SubType.SLITH})
@ManaCost("GG")
@Printings({@Printings.Printed(ex = Expansion.MIRRODIN, r = Rarity.UNCOMMON)})
@ColorIdentity({Color.GREEN})
public final class SlithPredator extends Card
{
	public static final class SlithGrowth extends EventTriggeredAbility
	{
		public SlithGrowth(GameState state)
		{
			super(state, "Whenever Slith Predator deals combat damage to a player, put a +1/+1 counter on it.");

			this.addPattern(whenDealsCombatDamageToAPlayer(ABILITY_SOURCE_OF_THIS));

			this.addEffect(putCountersOnThis(1, Counter.CounterType.PLUS_ONE_PLUS_ONE, "Slith Predator"));
		}
	}

	public SlithPredator(GameState state)
	{
		super(state);

		this.setPower(1);
		this.setToughness(1);

		// Trample
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Trample(state));

		// Whenever Slith Predator deals combat damage to a player, put a +1/+1
		// counter on it.
		this.addAbility(new SlithGrowth(state));
	}
}
