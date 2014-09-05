package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.expansions.*;

@Name("Thorn of Amethyst")
@Types({Type.ARTIFACT})
@ManaCost("2")
@Printings({@Printings.Printed(ex = Lorwyn.class, r = Rarity.RARE)})
@ColorIdentity({})
public final class ThornofAmethyst extends Card
{
	public static final class ThornofAmethystAbility0 extends StaticAbility
	{
		public ThornofAmethystAbility0(GameState state)
		{
			super(state, "Noncreature spells cost (1) more to cast.");

			ContinuousEffect.Part part = new ContinuousEffect.Part(ContinuousEffectType.COST_ADDITION);
			part.parameters.put(ContinuousEffectType.Parameter.OBJECT, RelativeComplement.instance(Spells.instance(), HasType.instance(Type.CREATURE)));
			part.parameters.put(ContinuousEffectType.Parameter.COST, numberGenerator(1));
			this.addEffectPart(part);
		}
	}

	public ThornofAmethyst(GameState state)
	{
		super(state);

		// Noncreature spells cost (1) more to cast.
		this.addAbility(new ThornofAmethystAbility0(state));
	}
}
