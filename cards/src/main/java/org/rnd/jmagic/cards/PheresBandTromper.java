package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;

@Name("Pheres-Band Tromper")
@Types({Type.CREATURE})
@SubTypes({SubType.CENTAUR, SubType.WARRIOR})
@ManaCost("3G")
@ColorIdentity({Color.GREEN})
public final class PheresBandTromper extends Card
{
	public static final class PheresBandTromperAbility0 extends EventTriggeredAbility
	{
		public PheresBandTromperAbility0(GameState state)
		{
			super(state, "Whenever Pheres-Band Tromper becomes untapped, put a +1/+1 counter on it.");
			this.addPattern(inspired());
			this.addEffect(putCountersOnThis(1, Counter.CounterType.PLUS_ONE_PLUS_ONE, "Put a +1/+1 counter on Pheres-Band Tromper."));
		}
	}

	public PheresBandTromper(GameState state)
	{
		super(state);

		this.setPower(3);
		this.setToughness(3);

		// Inspired \u2014 Whenever Pheres-Band Tromper becomes untapped, put a
		// +1/+1 counter on it.
		this.addAbility(new PheresBandTromperAbility0(state));
	}
}
