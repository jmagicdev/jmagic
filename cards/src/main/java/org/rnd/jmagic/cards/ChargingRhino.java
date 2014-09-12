package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Charging Rhino")
@Types({Type.CREATURE})
@SubTypes({SubType.RHINO})
@ManaCost("3GG")
@ColorIdentity({Color.GREEN})
public final class ChargingRhino extends Card
{
	public static final class ChargingRhinoAbility0 extends StaticAbility
	{
		public ChargingRhinoAbility0(GameState state)
		{
			super(state, "Charging Rhino can't be blocked by more than one creature.");

			SetGenerator blockingWithMoreThanOneCreature = Intersect.instance(Between.instance(2, null), Count.instance(Blocking.instance(This.instance())));

			ContinuousEffect.Part part = new ContinuousEffect.Part(ContinuousEffectType.BLOCKING_RESTRICTION);
			part.parameters.put(ContinuousEffectType.Parameter.RESTRICTION, Identity.instance(blockingWithMoreThanOneCreature));
			this.addEffectPart(part);
		}
	}

	public ChargingRhino(GameState state)
	{
		super(state);

		this.setPower(4);
		this.setToughness(4);

		// Charging Rhino can't be blocked by more than one creature.
		this.addAbility(new ChargingRhinoAbility0(state));
	}
}
