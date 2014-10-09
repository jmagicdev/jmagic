package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;

@Name("Pheres-Band Thunderhoof")
@Types({Type.CREATURE})
@SubTypes({SubType.CENTAUR, SubType.WARRIOR})
@ManaCost("4G")
@ColorIdentity({Color.GREEN})
public final class PheresBandThunderhoof extends Card
{
	public static final class PheresBandThunderhoofAbility0 extends EventTriggeredAbility
	{
		public PheresBandThunderhoofAbility0(GameState state)
		{
			super(state, "Heroic \u2014 Whenever you cast a spell that targets Pheres-Band Thunderhoof, put two +1/+1 counters on Pheres-Band Thunderhoof.");
			this.addPattern(heroic());
			this.addEffect(putCountersOnThis(2, Counter.CounterType.PLUS_ONE_PLUS_ONE, "Pheres-Band Thunderhoof"));
		}
	}

	public PheresBandThunderhoof(GameState state)
	{
		super(state);

		this.setPower(3);
		this.setToughness(4);

		// Heroic \u2014 Whenever you cast a spell that targets Pheres-Band
		// Thunderhoof, put two +1/+1 counters on Pheres-Band Thunderhoof.
		this.addAbility(new PheresBandThunderhoofAbility0(state));
	}
}
