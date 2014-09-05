package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.gameTypes.*;

@Name("Naar Isle")
@Types({Type.PLANE})
@SubTypes({SubType.WILDFIRE})
@Printings({@Printings.Printed(ex = org.rnd.jmagic.expansions.Planechase.class, r = Rarity.COMMON)})
@ColorIdentity({})
public final class NaarIsle extends Card
{
	public static final class Hottie extends EventTriggeredAbility
	{
		public Hottie(GameState state)
		{
			super(state, "At the beginning of your upkeep, put a flame counter on Naar Isle, then Naar Isle deals damage to you equal to the number of flame counters on it.");

			this.addPattern(atTheBeginningOfYourUpkeep());

			this.addEffect(putCountersOnThis(1, Counter.CounterType.FLAME, "Naar Isle"));

			this.addEffect(permanentDealDamage(Count.instance(CountersOn.instance(ABILITY_SOURCE_OF_THIS, Counter.CounterType.FLAME)), You.instance(), "Naar Isle deals damage to you equal to the number of flame counters on it."));

			this.canTrigger = PlanechaseGameRules.triggeredAbilityCanTrigger;
		}
	}

	public static final class ChaosSpike extends EventTriggeredAbility
	{
		public ChaosSpike(GameState state)
		{
			super(state, "Whenever you roll (C), Naar Isle deals 3 damage to target player.");

			this.addPattern(PlanechaseGameRules.wheneverYouRollChaos());

			Target target = this.addTarget(Players.instance(), "target player");

			this.addEffect(permanentDealDamage(3, targetedBy(target), "Naar Isle deals 3 damage to target player."));

			this.canTrigger = PlanechaseGameRules.triggeredAbilityCanTrigger;
		}
	}

	public NaarIsle(GameState state)
	{
		super(state);

		this.addAbility(new Hottie(state));

		this.addAbility(new ChaosSpike(state));
	}
}
