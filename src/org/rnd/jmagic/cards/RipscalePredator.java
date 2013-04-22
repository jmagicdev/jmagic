package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Ripscale Predator")
@Types({Type.CREATURE})
@SubTypes({SubType.LIZARD})
@ManaCost("4RR")
@Printings({@Printings.Printed(ex = Expansion.GATECRASH, r = Rarity.UNCOMMON)})
@ColorIdentity({Color.RED})
public final class RipscalePredator extends Card
{
	public static final class RipscalePredatorAbility0 extends StaticAbility
	{
		public RipscalePredatorAbility0(GameState state)
		{
			super(state, "Ripscale Predator can't be blocked except by two or more creatures.");
			SetGenerator restriction = Intersect.instance(Count.instance(Blocking.instance(This.instance())), numberGenerator(1));
			ContinuousEffect.Part part = new ContinuousEffect.Part(ContinuousEffectType.BLOCKING_RESTRICTION);
			part.parameters.put(ContinuousEffectType.Parameter.RESTRICTION, Identity.instance(restriction));
			this.addEffectPart(part);
		}
	}

	public RipscalePredator(GameState state)
	{
		super(state);

		this.setPower(6);
		this.setToughness(5);

		// Ripscale Predator can't be blocked except by two or more creatures.
		this.addAbility(new RipscalePredatorAbility0(state));
	}
}
