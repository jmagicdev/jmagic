package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Champion's Drake")
@Types({Type.CREATURE})
@SubTypes({SubType.DRAKE})
@ManaCost("1U")
@Printings({@Printings.Printed(ex = Expansion.RISE_OF_THE_ELDRAZI, r = Rarity.COMMON)})
@ColorIdentity({Color.BLUE})
public final class ChampionsDrake extends Card
{
	public static final class ConditionalPump extends StaticAbility
	{
		public ConditionalPump(GameState state)
		{
			super(state, "Champion's Drake gets +3/+3 as long as you control a creature with three or more level counters on it.");
			this.addEffectPart(modifyPowerAndToughness(This.instance(), +3, +3));

			this.canApply = Both.instance(this.canApply, Intersect.instance(CREATURES_YOU_CONTROL, HasCounterOfType.instance(3, Counter.CounterType.LEVEL)));
		}
	}

	public ChampionsDrake(GameState state)
	{
		super(state);

		this.setPower(1);
		this.setToughness(1);

		// Flying
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Flying(state));

		// Champion's Drake gets +3/+3 as long as you control a creature with
		// three or more level counters on it.
		this.addAbility(new ConditionalPump(state));
	}
}
