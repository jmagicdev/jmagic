package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Silent Arbiter")
@Types({Type.CREATURE, Type.ARTIFACT})
@SubTypes({SubType.CONSTRUCT})
@ManaCost("4")
@Printings({@Printings.Printed(ex = Expansion.FIFTH_DAWN, r = Rarity.RARE)})
@ColorIdentity({})
public final class SilentArbiter extends Card
{
	public static final class SilentArbiterAbility0 extends StaticAbility
	{
		public SilentArbiterAbility0(GameState state)
		{
			super(state, "No more than one creature can attack each combat.");

			ContinuousEffect.Part part = new ContinuousEffect.Part(ContinuousEffectType.ATTACKING_RESTRICTION);
			part.parameters.put(ContinuousEffectType.Parameter.RESTRICTION, Identity.instance(Intersect.instance(Count.instance(Attacking.instance()), Between.instance(numberGenerator(0), numberGenerator(1)))));
			this.addEffectPart(part);
		}
	}

	public static final class SilentArbiterAbility1 extends StaticAbility
	{
		public SilentArbiterAbility1(GameState state)
		{
			super(state, "No more than one creature can block each combat.");

			ContinuousEffect.Part part = new ContinuousEffect.Part(ContinuousEffectType.BLOCKING_RESTRICTION);
			part.parameters.put(ContinuousEffectType.Parameter.RESTRICTION, Identity.instance(Intersect.instance(Count.instance(Blocking.instance()), Between.instance(numberGenerator(0), numberGenerator(1)))));
			this.addEffectPart(part);
		}
	}

	public SilentArbiter(GameState state)
	{
		super(state);

		this.setPower(1);
		this.setToughness(5);

		// No more than one creature can attack each combat.
		this.addAbility(new SilentArbiterAbility0(state));

		// No more than one creature can block each combat.
		this.addAbility(new SilentArbiterAbility1(state));
	}
}
