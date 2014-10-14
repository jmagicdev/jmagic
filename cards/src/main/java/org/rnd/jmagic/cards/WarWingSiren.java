package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;

@Name("War-Wing Siren")
@Types({Type.CREATURE})
@SubTypes({SubType.SOLDIER, SubType.SIREN})
@ManaCost("2U")
@ColorIdentity({Color.BLUE})
public final class WarWingSiren extends Card
{
	public static final class WarWingSirenAbility1 extends EventTriggeredAbility
	{
		public WarWingSirenAbility1(GameState state)
		{
			super(state, "Heroic \u2014 Whenever you cast a spell that targets War-Wing Siren, put a +1/+1 counter on War-Wing Siren.");
			this.addPattern(heroic());
			this.addEffect(putCountersOnThis(1, Counter.CounterType.PLUS_ONE_PLUS_ONE, "War-Wing Siren"));
		}
	}

	public WarWingSiren(GameState state)
	{
		super(state);

		this.setPower(1);
		this.setToughness(3);

		// Flying
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Flying(state));

		// Heroic \u2014 Whenever you cast a spell that targets War-Wing Siren,
		// put a +1/+1 counter on War-Wing Siren.
		this.addAbility(new WarWingSirenAbility1(state));
	}
}
