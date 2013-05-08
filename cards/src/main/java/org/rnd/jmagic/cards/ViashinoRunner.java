package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Viashino Runner")
@Types({Type.CREATURE})
@SubTypes({SubType.VIASHINO})
@ManaCost("3R")
@Printings({@Printings.Printed(ex = Expansion.TENTH_EDITION, r = Rarity.COMMON), @Printings.Printed(ex = Expansion.URZAS_SAGA, r = Rarity.COMMON)})
@ColorIdentity({Color.RED})
public final class ViashinoRunner extends Card
{
	public static final class Running extends StaticAbility
	{
		public Running(GameState state)
		{
			super(state, "Viashino Runner can't be blocked except by two or more creatures.");

			SetGenerator blockingWithOneCreature = Intersect.instance(numberGenerator(1), Count.instance(Blocking.instance(This.instance())));

			ContinuousEffect.Part part = new ContinuousEffect.Part(ContinuousEffectType.BLOCKING_RESTRICTION);
			part.parameters.put(ContinuousEffectType.Parameter.RESTRICTION, Identity.instance(blockingWithOneCreature));
			this.addEffectPart(part);
		}
	}

	public ViashinoRunner(GameState state)
	{
		super(state);

		this.setPower(3);
		this.setToughness(2);

		// Viashino Runner can't be blocked except by two or more creatures.
		this.addAbility(new Running(state));
	}
}
