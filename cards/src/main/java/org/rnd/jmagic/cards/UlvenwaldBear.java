package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Ulvenwald Bear")
@Types({Type.CREATURE})
@SubTypes({SubType.BEAR})
@ManaCost("2G")
@Printings({@Printings.Printed(ex = Expansion.DARK_ASCENSION, r = Rarity.COMMON)})
@ColorIdentity({Color.GREEN})
public final class UlvenwaldBear extends Card
{
	public static final class UlvenwaldBearAbility0 extends EventTriggeredAbility
	{
		public UlvenwaldBearAbility0(GameState state)
		{
			super(state, "When Ulvenwald Bear enters the battlefield, if a creature died this turn, put two +1/+1 counters on target creature.");
			this.addPattern(whenThisEntersTheBattlefield());
			this.interveningIf = Morbid.instance();

			state.ensureTracker(new Morbid.Tracker());

			SetGenerator target = targetedBy(this.addTarget(CreaturePermanents.instance(), "target creature"));
			this.addEffect(putCounters(2, Counter.CounterType.PLUS_ONE_PLUS_ONE, target, "Put two +1/+1 counters on target creature."));
		}
	}

	public UlvenwaldBear(GameState state)
	{
		super(state);

		this.setPower(2);
		this.setToughness(2);

		// Morbid \u2014 When Ulvenwald Bear enters the battlefield, if a
		// creature died this turn, put two +1/+1 counters on target creature.
		this.addAbility(new UlvenwaldBearAbility0(state));
	}
}
