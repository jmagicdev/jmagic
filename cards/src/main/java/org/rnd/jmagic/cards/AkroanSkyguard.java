package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;

@Name("Akroan Skyguard")
@Types({Type.CREATURE})
@SubTypes({SubType.HUMAN, SubType.SOLDIER})
@ManaCost("1W")
@ColorIdentity({Color.WHITE})
public final class AkroanSkyguard extends Card
{
	public static final class AkroanSkyguardAbility1 extends EventTriggeredAbility
	{
		public AkroanSkyguardAbility1(GameState state)
		{
			super(state, "Heroic \u2014 Whenever you cast a spell that targets Akroan Skyguard, put a +1/+1 counter on Akroan Skyguard.");
			this.addPattern(heroic());
			this.addEffect(putCountersOnThis(1, Counter.CounterType.PLUS_ONE_PLUS_ONE, "Put a +1/+1 counter on Akroan Skyguard."));
		}
	}

	public AkroanSkyguard(GameState state)
	{
		super(state);

		this.setPower(1);
		this.setToughness(1);

		// Flying
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Flying(state));

		// Whenever you cast a spell that targets Akroan Skyguard, put a +1/+1
		// counter on Akroan Skyguard.
		this.addAbility(new AkroanSkyguardAbility1(state));
	}
}
