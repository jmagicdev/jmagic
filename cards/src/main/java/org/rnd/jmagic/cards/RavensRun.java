package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.gameTypes.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Raven's Run")
@Types({Type.PLANE})
@SubTypes({SubType.SHADOWMOOR})
@Printings({@Printings.Printed(ex = Expansion.PLANECHASE, r = Rarity.COMMON)})
@ColorIdentity({})
public final class RavensRun extends Card
{
	public static final class GrantWither extends StaticAbility
	{
		public GrantWither(GameState state)
		{
			super(state, "All creatures have wither.");

			this.addEffectPart(addAbilityToObject(CreaturePermanents.instance(), org.rnd.jmagic.abilities.keywords.Wither.class));

			this.canApply = Planechase.staticAbilityCanApply;
		}
	}

	public static final class IncrementalChaos extends EventTriggeredAbility
	{
		public IncrementalChaos(GameState state)
		{
			super(state, "Whenever you roll (C), put a -1/-1 counter on target creature, two -1/-1 counters on another target creature, and three -1/-1 counters on a third target creature.");

			this.addPattern(Planechase.wheneverYouRollChaos());

			Target target1 = this.addTarget(CreaturePermanents.instance(), "target creature to put one -1/-1 counter on");
			Target target2 = this.addTarget(CreaturePermanents.instance(), "target creature to put two -1/-1 counters on");
			Target target3 = this.addTarget(CreaturePermanents.instance(), "target creature to put three -1/-1 counters on");

			this.addEffect(putCounters(1, Counter.CounterType.MINUS_ONE_MINUS_ONE, targetedBy(target1), "Put a -1/-1 counter on target creature."));
			this.addEffect(putCounters(2, Counter.CounterType.MINUS_ONE_MINUS_ONE, targetedBy(target2), "Put two -1/-1 counters on another target creature."));
			this.addEffect(putCounters(3, Counter.CounterType.MINUS_ONE_MINUS_ONE, targetedBy(target3), "Put three -1/-1 counters on a third target creature."));

			this.canTrigger = Planechase.triggeredAbilityCanTrigger;
		}
	}

	public RavensRun(GameState state)
	{
		super(state);

		this.addAbility(new GrantWither(state));

		this.addAbility(new IncrementalChaos(state));
	}
}
