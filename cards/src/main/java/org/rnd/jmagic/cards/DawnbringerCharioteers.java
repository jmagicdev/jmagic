package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;

@Name("Dawnbringer Charioteers")
@Types({Type.CREATURE})
@SubTypes({SubType.SOLDIER, SubType.HUMAN})
@ManaCost("2WW")
@ColorIdentity({Color.WHITE})
public final class DawnbringerCharioteers extends Card
{
	public static final class DawnbringerCharioteersAbility1 extends EventTriggeredAbility
	{
		public DawnbringerCharioteersAbility1(GameState state)
		{
			super(state, "Heroic \u2014 Whenever you cast a spell that targets Dawnbringer Charioteers, put a +1/+1 counter on Dawnbringer Charioteers.");
			this.addPattern(heroic());
			this.addEffect(putCountersOnThis(1, Counter.CounterType.PLUS_ONE_PLUS_ONE, "Dawnbringer Charioteers"));
		}
	}

	public DawnbringerCharioteers(GameState state)
	{
		super(state);

		this.setPower(2);
		this.setToughness(4);

		// Flying, lifelink
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Flying(state));
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Lifelink(state));

		// Heroic \u2014 Whenever you cast a spell that targets Dawnbringer
		// Charioteers, put a +1/+1 counter on Dawnbringer Charioteers.
		this.addAbility(new DawnbringerCharioteersAbility1(state));
	}
}
