package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Timberland Guide")
@Types({Type.CREATURE})
@SubTypes({SubType.HUMAN, SubType.SCOUT})
@ManaCost("1G")
@Printings({@Printings.Printed(ex = Expansion.AVACYN_RESTORED, r = Rarity.COMMON)})
@ColorIdentity({Color.GREEN})
public final class TimberlandGuide extends Card
{
	public static final class TimberlandGuideAbility0 extends EventTriggeredAbility
	{
		public TimberlandGuideAbility0(GameState state)
		{
			super(state, "When Timberland Guide enters the battlefield, put a +1/+1 counter on target creature.");
			this.addPattern(whenThisEntersTheBattlefield());
			SetGenerator target = targetedBy(this.addTarget(CreaturePermanents.instance(), "target creature"));
			this.addEffect(putCounters(1, Counter.CounterType.PLUS_ONE_PLUS_ONE, target, "Put a +1/+1 counter on target creature."));
		}
	}

	public TimberlandGuide(GameState state)
	{
		super(state);

		this.setPower(1);
		this.setToughness(1);

		// When Timberland Guide enters the battlefield, put a +1/+1 counter on
		// target creature.
		this.addAbility(new TimberlandGuideAbility0(state));
	}
}
