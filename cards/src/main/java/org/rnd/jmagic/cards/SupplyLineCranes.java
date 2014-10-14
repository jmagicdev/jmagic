package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Supply-Line Cranes")
@Types({Type.CREATURE})
@SubTypes({SubType.BIRD})
@ManaCost("3WW")
@ColorIdentity({Color.WHITE})
public final class SupplyLineCranes extends Card
{
	public static final class SupplyLineCranesAbility1 extends EventTriggeredAbility
	{
		public SupplyLineCranesAbility1(GameState state)
		{
			super(state, "When Supply-Line Cranes enters the battlefield, put a +1/+1 counter on target creature.");
			this.addPattern(whenThisEntersTheBattlefield());
			SetGenerator target = targetedBy(this.addTarget(CreaturePermanents.instance(), "target creature"));
			this.addEffect(putCounters(1, Counter.CounterType.PLUS_ONE_PLUS_ONE, target, "Put a +1/+1 counter on target creature."));
		}
	}

	public SupplyLineCranes(GameState state)
	{
		super(state);

		this.setPower(2);
		this.setToughness(4);

		// Flying
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Flying(state));

		// When Supply-Line Cranes enters the battlefield, put a +1/+1 counter
		// on target creature.
		this.addAbility(new SupplyLineCranesAbility1(state));
	}
}
