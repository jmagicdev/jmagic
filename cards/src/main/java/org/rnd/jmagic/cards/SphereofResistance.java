package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Sphere of Resistance")
@Types({Type.ARTIFACT})
@ManaCost("2")
@ColorIdentity({})
public final class SphereofResistance extends Card
{
	public static final class SphereofResistanceAbility0 extends StaticAbility
	{
		public SphereofResistanceAbility0(GameState state)
		{
			super(state, "Spells cost (1) more to cast.");

			ContinuousEffect.Part part = new ContinuousEffect.Part(ContinuousEffectType.COST_ADDITION);
			part.parameters.put(ContinuousEffectType.Parameter.OBJECT, Spells.instance());
			part.parameters.put(ContinuousEffectType.Parameter.COST, numberGenerator(1));
			this.addEffectPart(part);
		}
	}

	public SphereofResistance(GameState state)
	{
		super(state);

		// Spells cost (1) more to cast.
		this.addAbility(new SphereofResistanceAbility0(state));
	}
}
