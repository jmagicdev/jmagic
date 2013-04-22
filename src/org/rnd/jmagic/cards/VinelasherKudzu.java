package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;

@Name("Vinelasher Kudzu")
@Types({Type.CREATURE})
@SubTypes({SubType.PLANT})
@ManaCost("1G")
@Printings({@Printings.Printed(ex = Expansion.RAVNICA, r = Rarity.RARE)})
@ColorIdentity({Color.GREEN})
public final class VinelasherKudzu extends Card
{
	public static final class LandfallCounters extends EventTriggeredAbility
	{
		public LandfallCounters(GameState state)
		{
			super(state, "Whenever a land enters the battlefield under your control, put a +1/+1 counter on Vinelasher Kudzu.");
			this.addPattern(landfall());
			this.addEffect(putCountersOnThis(1, Counter.CounterType.PLUS_ONE_PLUS_ONE, "Put a +1/+1 counter on Vinelasher Kudzu."));
		}
	}

	public VinelasherKudzu(GameState state)
	{
		super(state);

		this.setPower(1);
		this.setToughness(1);

		// Whenever a land enters the battlefield under your control, put a
		// +1/+1 counter on Vinelasher Kudzu.
		this.addAbility(new LandfallCounters(state));
	}
}
