package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Ironshell Beetle")
@Types({Type.CREATURE})
@SubTypes({SubType.INSECT})
@ManaCost("1G")
@Printings({@Printings.Printed(ex = Expansion.JUDGMENT, r = Rarity.COMMON)})
@ColorIdentity({Color.GREEN})
public final class IronshellBeetle extends Card
{
	public static final class IronshellBeetleAbility extends EventTriggeredAbility
	{
		public IronshellBeetleAbility(GameState state)
		{
			super(state, "When Ironshell Beetle enters the battlefield, put a +1/+1 counter on target creature.");
			this.addPattern(whenThisEntersTheBattlefield());
			Target target = this.addTarget(CreaturePermanents.instance(), "target creature");
			this.addEffect(putCounters(1, Counter.CounterType.PLUS_ONE_PLUS_ONE, targetedBy(target), "Put a +1/+1 counter on target creature."));
		}
	}

	public IronshellBeetle(GameState state)
	{
		super(state);

		this.setPower(1);
		this.setToughness(1);

		this.addAbility(new IronshellBeetleAbility(state));
	}
}
