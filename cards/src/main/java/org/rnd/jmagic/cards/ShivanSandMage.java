package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.expansions.*;

@Name("Shivan Sand-Mage")
@Types({Type.CREATURE})
@SubTypes({SubType.SHAMAN, SubType.VIASHINO})
@ManaCost("2RR")
@Printings({@Printings.Printed(ex = FutureSight.class, r = Rarity.UNCOMMON)})
@ColorIdentity({Color.RED})
public final class ShivanSandMage extends Card
{
	public static final class ShivanSandMageAbility0 extends EventTriggeredAbility
	{
		public ShivanSandMageAbility0(GameState state)
		{
			super(state, "When Shivan Sand-Mage enters the battlefield, choose one \u2014\n\u2022 Remove two time counters from target permanent or suspended card.\n\u2022 Put two time counters on target permanent with a time counter on it or suspended card.");
			this.addPattern(whenThisEntersTheBattlefield());

			SetGenerator targetOne = targetedBy(this.addTarget(1, Union.instance(Permanents.instance(), Suspended.instance()), "target permanent or suspended card"));
			this.addEffect(1, removeCounters(2, Counter.CounterType.TIME, targetOne, "Remove two time counters from target permanent or suspended card"));

			SetGenerator targetTwo = targetedBy(this.addTarget(2, Union.instance(Intersect.instance(Permanents.instance(), HasCounterOfType.instance(Counter.CounterType.TIME)), Suspended.instance()), "target permanent with a time counter on it or suspended card"));
			this.addEffect(2, putCounters(2, Counter.CounterType.TIME, targetTwo, "put two time counters on target permanent with a time counter on it or suspended card."));
		}
	}

	public ShivanSandMage(GameState state)
	{
		super(state);

		this.setPower(3);
		this.setToughness(2);

		// When Shivan Sand-Mage enters the battlefield, choose one \u2014
		// Remove two time counters from target permanent or suspended card; or
		// put two time counters on target permanent with a time counter on it
		// or suspended card.
		this.addAbility(new ShivanSandMageAbility0(state));

		// Suspend 4\u2014(R)
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Suspend(state, 4, "R"));
	}
}
