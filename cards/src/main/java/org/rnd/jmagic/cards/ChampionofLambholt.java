package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Champion of Lambholt")
@Types({Type.CREATURE})
@SubTypes({SubType.WARRIOR, SubType.HUMAN})
@ManaCost("1GG")
@ColorIdentity({Color.GREEN})
public final class ChampionofLambholt extends Card
{
	public static final class ChampionofLambholtAbility0 extends StaticAbility
	{
		public ChampionofLambholtAbility0(GameState state)
		{
			super(state, "Creatures with power less than Champion of Lambholt's power can't block creatures you control.");

			SetGenerator relevantCreatures = HasPower.instance(Between.instance(Empty.instance(), Subtract.instance(PowerOf.instance(This.instance()), numberGenerator(1))));

			ContinuousEffect.Part part = new ContinuousEffect.Part(ContinuousEffectType.BLOCKING_RESTRICTION);
			part.parameters.put(ContinuousEffectType.Parameter.RESTRICTION, Identity.instance(Intersect.instance(Blocking.instance(CREATURES_YOU_CONTROL), relevantCreatures)));
			this.addEffectPart(part);
		}
	}

	public static final class ChampionofLambholtAbility1 extends EventTriggeredAbility
	{
		public ChampionofLambholtAbility1(GameState state)
		{
			super(state, "Whenever another creature enters the battlefield under your control, put a +1/+1 counter on Champion of Lambholt.");
			this.addPattern(new org.rnd.jmagic.engine.patterns.SimpleZoneChangePattern(null, Battlefield.instance(), RelativeComplement.instance(CreaturePermanents.instance(), ABILITY_SOURCE_OF_THIS), You.instance(), false));
			this.addEffect(putCounters(1, Counter.CounterType.PLUS_ONE_PLUS_ONE, ABILITY_SOURCE_OF_THIS, "Put a +1/+1 counter on Champion of Lambholt."));
		}
	}

	public ChampionofLambholt(GameState state)
	{
		super(state);

		this.setPower(1);
		this.setToughness(1);

		// Creatures with power less than Champion of Lambholt's power can't
		// block creatures you control.
		this.addAbility(new ChampionofLambholtAbility0(state));

		// Whenever another creature enters the battlefield under your control,
		// put a +1/+1 counter on Champion of Lambholt.
		this.addAbility(new ChampionofLambholtAbility1(state));
	}
}
