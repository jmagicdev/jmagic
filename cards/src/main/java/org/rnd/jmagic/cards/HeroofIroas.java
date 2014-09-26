package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Hero of Iroas")
@Types({Type.CREATURE})
@SubTypes({SubType.HUMAN, SubType.SOLDIER})
@ManaCost("1W")
@ColorIdentity({Color.WHITE})
public final class HeroofIroas extends Card
{
	public static final class HeroofIroasAbility1 extends EventTriggeredAbility
	{
		public HeroofIroasAbility1(GameState state)
		{
			super(state, "Heroic \u2014 Whenever you cast a spell that targets Hero of Iroas, put a +1/+1 counter on Hero of Iroas.");
			this.addPattern(heroic());
			this.addEffect(putCountersOnThis(1, Counter.CounterType.PLUS_ONE_PLUS_ONE, "Put a +1/+1 counter on Hero of Iroas."));
		}
	}

	public HeroofIroas(GameState state)
	{
		super(state);

		this.setPower(2);
		this.setToughness(2);

		// Aura spells you cast cost (1) less to cast.
		this.addAbility(new org.rnd.jmagic.abilities.CostsYouLessToCast(state, HasSubType.instance(SubType.AURA), "(1)", "Aura spells you cast cost (1) less to cast."));

		// Heroic \u2014 Whenever you cast a spell that targets Hero of Iroas,
		// put a +1/+1 counter on Hero of Iroas.
		this.addAbility(new HeroofIroasAbility1(state));
	}
}
