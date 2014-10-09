package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;

@Name("Lagonna-Band Trailblazer")
@Types({Type.CREATURE})
@SubTypes({SubType.CENTAUR, SubType.SCOUT})
@ManaCost("W")
@ColorIdentity({Color.WHITE})
public final class LagonnaBandTrailblazer extends Card
{
	public static final class LagonnaBandTrailblazerAbility0 extends EventTriggeredAbility
	{
		public LagonnaBandTrailblazerAbility0(GameState state)
		{
			super(state, "Heroic \u2014 Whenever you cast a spell that targets Lagonna-Band Trailblazer, put a +1/+1 counter on Lagonna-Band Trailblazer.");
			this.addPattern(heroic());
			this.addEffect(putCountersOnThis(1, Counter.CounterType.PLUS_ONE_PLUS_ONE, "Lagonna-Band Trailblazer"));
		}
	}

	public LagonnaBandTrailblazer(GameState state)
	{
		super(state);

		this.setPower(0);
		this.setToughness(4);

		// Heroic \u2014 Whenever you cast a spell that targets Lagonna-Band
		// Trailblazer, put a +1/+1 counter on Lagonna-Band Trailblazer.
		this.addAbility(new LagonnaBandTrailblazerAbility0(state));
	}
}
