package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.engine.patterns.*;

@Name("Hardened Scales")
@Types({Type.ENCHANTMENT})
@ManaCost("G")
@ColorIdentity({Color.GREEN})
public final class HardenedScales extends Card
{
	public static final class HardenedScalesAbility0 extends StaticAbility
	{
		public HardenedScalesAbility0(GameState state)
		{
			super(state, "If one or more +1/+1 counters would be placed on a creature you control, that many plus one +1/+1 counters are placed on it instead.");

			EventPattern getCounters = new CountersPlacedPattern(Counter.CounterType.PLUS_ONE_PLUS_ONE, new YouControlPattern(new TypePattern(Type.CREATURE)));
			EventReplacementEffect replacement = new EventReplacementEffect(state.game, "If one or more +1/+1 counters would be placed on a creature you control, that many plus one +1/+1 counters are placed on it instead.", getCounters);

			SetGenerator thatManyPlusOne = Sum.instance(Union.instance(numberGenerator(1), EventParameter.instance(replacement.replacedByThis(), EventType.Parameter.NUMBER)));

			EventFactory getMoreCounters = new EventFactory(EventType.PUT_COUNTERS, "Twice that many +1/+1 counters are placed on it instead");
			getMoreCounters.parameters.put(EventType.Parameter.NUMBER, thatManyPlusOne);
			replacement.addEffect(getMoreCounters);

			this.addEffectPart(replacementEffectPart(replacement));

		}
	}

	public HardenedScales(GameState state)
	{
		super(state);

		// If one or more +1/+1 counters would be placed on a creature you
		// control, that many plus one +1/+1 counters are placed on it instead.
		this.addAbility(new HardenedScalesAbility0(state));
	}
}
