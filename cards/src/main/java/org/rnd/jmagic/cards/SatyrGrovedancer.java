package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Satyr Grovedancer")
@Types({Type.CREATURE})
@SubTypes({SubType.SATYR, SubType.SHAMAN})
@ManaCost("1G")
@ColorIdentity({Color.GREEN})
public final class SatyrGrovedancer extends Card
{
	public static final class SatyrGrovedancerAbility0 extends EventTriggeredAbility
	{
		public SatyrGrovedancerAbility0(GameState state)
		{
			super(state, "When Satyr Grovedancer enters the battlefield, put a +1/+1 counter on target creature.");
			this.addPattern(whenThisEntersTheBattlefield());
			SetGenerator target = targetedBy(this.addTarget(CreaturePermanents.instance(), "target creature"));
			this.addEffect(putCounters(1, Counter.CounterType.PLUS_ONE_PLUS_ONE, target, "Put a +1/+1 counter on target creature."));
		}
	}

	public SatyrGrovedancer(GameState state)
	{
		super(state);

		this.setPower(1);
		this.setToughness(1);

		// When Satyr Grovedancer enters the battlefield, put a +1/+1 counter on
		// target creature.
		this.addAbility(new SatyrGrovedancerAbility0(state));
	}
}
