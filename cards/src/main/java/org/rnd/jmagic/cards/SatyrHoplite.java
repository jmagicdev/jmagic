package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;

@Name("Satyr Hoplite")
@Types({Type.CREATURE})
@SubTypes({SubType.SOLDIER, SubType.SATYR})
@ManaCost("R")
@ColorIdentity({Color.RED})
public final class SatyrHoplite extends Card
{
	public static final class SatyrHopliteAbility0 extends EventTriggeredAbility
	{
		public SatyrHopliteAbility0(GameState state)
		{
			super(state, "Heroic \u2014 Whenever you cast a spell that targets Satyr Hoplite, put a +1/+1 counter on Satyr Hoplite.");
			this.addPattern(heroic());
			this.addEffect(putCountersOnThis(1, Counter.CounterType.PLUS_ONE_PLUS_ONE, "Satyr Hoplite"));
		}
	}

	public SatyrHoplite(GameState state)
	{
		super(state);

		this.setPower(1);
		this.setToughness(1);

		// Heroic \u2014 Whenever you cast a spell that targets Satyr Hoplite,
		// put a +1/+1 counter on Satyr Hoplite.
		this.addAbility(new SatyrHopliteAbility0(state));
	}
}
