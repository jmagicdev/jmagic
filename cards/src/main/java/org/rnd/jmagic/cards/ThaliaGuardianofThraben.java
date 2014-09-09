package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Thalia, Guardian of Thraben")
@SuperTypes({SuperType.LEGENDARY})
@Types({Type.CREATURE})
@SubTypes({SubType.SOLDIER, SubType.HUMAN})
@ManaCost("1W")
@ColorIdentity({Color.WHITE})
public final class ThaliaGuardianofThraben extends Card
{
	public static final class ThaliaGuardianofThrabenAbility1 extends StaticAbility
	{
		public ThaliaGuardianofThrabenAbility1(GameState state)
		{
			super(state, "Noncreature spells cost (1) more to cast.");

			ContinuousEffect.Part part = new ContinuousEffect.Part(ContinuousEffectType.COST_ADDITION);
			part.parameters.put(ContinuousEffectType.Parameter.OBJECT, RelativeComplement.instance(Spells.instance(), HasType.instance(Type.CREATURE)));
			part.parameters.put(ContinuousEffectType.Parameter.COST, numberGenerator(1));
			this.addEffectPart(part);
		}
	}

	public ThaliaGuardianofThraben(GameState state)
	{
		super(state);

		this.setPower(2);
		this.setToughness(1);

		// First strike
		this.addAbility(new org.rnd.jmagic.abilities.keywords.FirstStrike(state));

		// Noncreature spells cost (1) more to cast.
		this.addAbility(new ThaliaGuardianofThrabenAbility1(state));
	}
}
