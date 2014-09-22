package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.patterns.*;

@Name("Corpsejack Menace")
@Types({Type.CREATURE})
@SubTypes({SubType.FUNGUS})
@ManaCost("2BG")
@ColorIdentity({Color.BLACK, Color.GREEN})
public final class CorpsejackMenace extends Card
{
	public static final class CorpsejackMenaceAbility0 extends StaticAbility
	{
		public CorpsejackMenaceAbility0(GameState state)
		{
			super(state, "If one or more +1/+1 counters would be placed on a creature you control, twice that many +1/+1 counters are placed on it instead.");

			EventPattern getCounters = new CounterPlacedPattern(Counter.CounterType.PLUS_ONE_PLUS_ONE, new YouControlPattern(new TypePattern(Type.CREATURE)));
			EventReplacementEffect replacement = new EventReplacementEffect(state.game, "If one or more +1/+1 counters would be placed on a creature you control, twice that many +1/+1 counters are placed on it instead.", getCounters);

			EventFactory getMoreCounters = new EventFactory(EventType.PUT_COUNTERS, "Twice that many +1/+1 counters are placed on it instead");
			getMoreCounters.parameters.put(EventType.Parameter.NUMBER, numberGenerator(2));
			replacement.addEffect(getMoreCounters);

			this.addEffectPart(replacementEffectPart(replacement));
		}
	}

	public CorpsejackMenace(GameState state)
	{
		super(state);

		this.setPower(4);
		this.setToughness(4);

		// If one or more +1/+1 counters would be placed on a creature you
		// control, twice that many +1/+1 counters are placed on it instead.
		this.addAbility(new CorpsejackMenaceAbility0(state));
	}
}
