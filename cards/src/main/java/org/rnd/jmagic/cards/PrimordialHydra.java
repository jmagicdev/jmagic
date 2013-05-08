package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Primordial Hydra")
@Types({Type.CREATURE})
@SubTypes({SubType.HYDRA})
@ManaCost("XGG")
@Printings({@Printings.Printed(ex = Expansion.MAGIC_2013, r = Rarity.MYTHIC), @Printings.Printed(ex = Expansion.MAGIC_2012, r = Rarity.MYTHIC)})
@ColorIdentity({Color.GREEN})
public final class PrimordialHydra extends Card
{
	public static final class PrimordialHydraAbility1 extends EventTriggeredAbility
	{
		public PrimordialHydraAbility1(GameState state)
		{
			super(state, "At the beginning of your upkeep, double the number of +1/+1 counters on Primordial Hydra.");
			this.addPattern(atTheBeginningOfYourUpkeep());

			SetGenerator number = Count.instance(CountersOn.instance(ABILITY_SOURCE_OF_THIS, Counter.CounterType.PLUS_ONE_PLUS_ONE));
			this.addEffect(putCounters(number, Counter.CounterType.PLUS_ONE_PLUS_ONE, ABILITY_SOURCE_OF_THIS, "Double the number of +1/+1 counters on Primordial Hydra."));
		}
	}

	public static final class PrimordialHydraAbility2 extends StaticAbility
	{
		public PrimordialHydraAbility2(GameState state)
		{
			super(state, "Primordial Hydra has trample as long as it has ten or more +1/+1 counters on it.");

			SetGenerator counters = Count.instance(CountersOn.instance(This.instance(), Counter.CounterType.PLUS_ONE_PLUS_ONE));
			SetGenerator condition = Intersect.instance(counters, Between.instance(10, null));
			this.canApply = Both.instance(this.canApply, condition);

			this.addEffectPart(addAbilityToObject(This.instance(), org.rnd.jmagic.abilities.keywords.Trample.class));
		}
	}

	public PrimordialHydra(GameState state)
	{
		super(state);

		this.setPower(0);
		this.setToughness(0);

		// Primordial Hydra enters the battlefield with X +1/+1 counters on it.
		this.addAbility(new org.rnd.jmagic.abilities.EntersTheBattlefieldWithCounters(state, this.getName(), ValueOfX.instance(This.instance()), "X +1/+1 counters on it", Counter.CounterType.PLUS_ONE_PLUS_ONE));

		// At the beginning of your upkeep, double the number of +1/+1 counters
		// on Primordial Hydra.
		this.addAbility(new PrimordialHydraAbility1(state));

		// Primordial Hydra has trample as long as it has ten or more +1/+1
		// counters on it.
		this.addAbility(new PrimordialHydraAbility2(state));
	}
}
