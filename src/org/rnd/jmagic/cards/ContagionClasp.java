package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Contagion Clasp")
@Types({Type.ARTIFACT})
@ManaCost("2")
@Printings({@Printings.Printed(ex = Expansion.SCARS_OF_MIRRODIN, r = Rarity.UNCOMMON)})
@ColorIdentity({})
public final class ContagionClasp extends Card
{
	public static final class ContagionClaspAbility0 extends EventTriggeredAbility
	{
		public ContagionClaspAbility0(GameState state)
		{
			super(state, "When Contagion Clasp enters the battlefield, put a -1/-1 counter on target creature.");
			this.addPattern(whenThisEntersTheBattlefield());
			SetGenerator target = targetedBy(this.addTarget(CreaturePermanents.instance(), "target creature"));
			this.addEffect(putCounters(1, Counter.CounterType.MINUS_ONE_MINUS_ONE, target, "Put a -1/-1 counter on target creature."));
		}
	}

	public static final class ContagionClaspAbility1 extends ActivatedAbility
	{
		public ContagionClaspAbility1(GameState state)
		{
			super(state, "(4), (T): Proliferate.");
			this.setManaCost(new ManaPool("(4)"));
			this.costsTap = true;
			this.addEffect(proliferate());
		}
	}

	public ContagionClasp(GameState state)
	{
		super(state);

		// When Contagion Clasp enters the battlefield, put a -1/-1 counter on
		// target creature.
		this.addAbility(new ContagionClaspAbility0(state));

		// (4), (T): Proliferate. (You choose any number of permanents and/or
		// players with counters on them, then give each another counter of a
		// kind already there.)
		this.addAbility(new ContagionClaspAbility1(state));
	}
}
