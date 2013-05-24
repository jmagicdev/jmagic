package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Lodestone Golem")
@Types({Type.CREATURE, Type.ARTIFACT})
@SubTypes({SubType.GOLEM})
@ManaCost("4")
@Printings({@Printings.Printed(ex = Expansion.WORLDWAKE, r = Rarity.RARE)})
@ColorIdentity({})
public final class LodestoneGolem extends Card
{
	public static final class NonartifactsCostMore extends StaticAbility
	{
		public NonartifactsCostMore(GameState state)
		{
			super(state, "Nonartifact spells cost (1) more to cast.");

			ContinuousEffect.Part part = new ContinuousEffect.Part(ContinuousEffectType.COST_ADDITION);
			part.parameters.put(ContinuousEffectType.Parameter.OBJECT, RelativeComplement.instance(Spells.instance(), HasType.instance(Type.ARTIFACT)));
			part.parameters.put(ContinuousEffectType.Parameter.COST, numberGenerator(1));
			this.addEffectPart(part);
		}
	}

	public LodestoneGolem(GameState state)
	{
		super(state);

		this.setPower(5);
		this.setToughness(3);

		// Nonartifact spells cost (1) more to cast.
		this.addAbility(new NonartifactsCostMore(state));
	}
}
