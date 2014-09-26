package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Hooded Hydra")
@Types({Type.CREATURE})
@SubTypes({SubType.SNAKE, SubType.HYDRA})
@ManaCost("XGG")
@ColorIdentity({Color.GREEN})
public final class HoodedHydra extends Card
{
	public static final class HoodedHydraAbility1 extends EventTriggeredAbility
	{
		public HoodedHydraAbility1(GameState state)
		{
			super(state, "When Hooded Hydra dies, put a 1/1 green Snake creature token onto the battlefield for each +1/+1 counter on it.");
			this.addPattern(whenThisDies());

			SetGenerator X = Count.instance(CountersOn.instance(ABILITY_SOURCE_OF_THIS, Counter.CounterType.PLUS_ONE_PLUS_ONE));

			CreateTokensFactory snakes = new CreateTokensFactory(X, numberGenerator(1), numberGenerator(1), "");
			snakes.setColors(Color.GREEN);
			snakes.setSubTypes(SubType.SNAKE);
			this.addEffect(snakes.getEventFactory());
		}
	}

	public static final class HoodedHydraAbility3 extends StaticAbility
	{
		public HoodedHydraAbility3(GameState state)
		{
			super(state, "As Hooded Hydra is turned face up, put five +1/+1 counters on it.");

			EventReplacementEffect replacement = new EventReplacementEffect(state.game, "As Hooded Hydra is turned face up, put five +1/+1 counters on it.", asThisIsTurnedFaceUp());
			replacement.addEffect(putCounters(5, Counter.CounterType.PLUS_ONE_PLUS_ONE, This.instance(), "Put five +1/+1 counters on Hooded Hydra."));
			this.addEffectPart(replacementEffectPart(replacement));
		}
	}

	public HoodedHydra(GameState state)
	{
		super(state);

		this.setPower(0);
		this.setToughness(0);

		// Hooded Hydra enters the battlefield with X +1/+1 counters on it.
		this.addAbility(new org.rnd.jmagic.abilities.EntersTheBattlefieldWithCounters(//
		state, this.getName(), ValueOfX.instance(This.instance()), "X +1/+1 counters on it", Counter.CounterType.PLUS_ONE_PLUS_ONE));

		// When Hooded Hydra dies, put a 1/1 green Snake creature token onto the
		// battlefield for each +1/+1 counter on it.
		this.addAbility(new HoodedHydraAbility1(state));

		// Morph (3)(G)(G)
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Morph(state, "(3)(G)(G)"));

		// As Hooded Hydra is turned face up, put five +1/+1 counters on it.
		this.addAbility(new HoodedHydraAbility3(state));
	}
}
