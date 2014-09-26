package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;

@Name("Setessan Oathsworn")
@Types({Type.CREATURE})
@SubTypes({SubType.SATYR, SubType.WARRIOR})
@ManaCost("1GG")
@ColorIdentity({Color.GREEN})
public final class SetessanOathsworn extends Card
{
	public static final class SetessanOathswornAbility0 extends EventTriggeredAbility
	{
		public SetessanOathswornAbility0(GameState state)
		{
			super(state, "Heroic \u2014 Whenever you cast a spell that targets Setessan Oathsworn, put two +1/+1 counters on Setessan Oathsworn.");
			this.addPattern(heroic());

			this.addEffect(putCountersOnThis(2, Counter.CounterType.PLUS_ONE_PLUS_ONE, "Put two +1/+1 counters on Setessan Oathsworn."));
		}
	}

	public SetessanOathsworn(GameState state)
	{
		super(state);

		this.setPower(1);
		this.setToughness(1);

		// Heroic \u2014 Whenever you cast a spell that targets Setessan
		// Oathsworn, put two +1/+1 counters on Setessan Oathsworn.
		this.addAbility(new SetessanOathswornAbility0(state));
	}
}
