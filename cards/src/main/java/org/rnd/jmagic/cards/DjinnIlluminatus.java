package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Djinn Illuminatus")
@Types({Type.CREATURE})
@SubTypes({SubType.DJINN})
@ManaCost("5(U/R)(U/R)")
@ColorIdentity({Color.BLUE, Color.RED})
public final class DjinnIlluminatus extends Card
{
	public static final class Replicant extends StaticAbility
	{
		public Replicant(GameState state)
		{
			super(state, "Each instant and sorcery spell you cast has replicate. The replicate cost is equal to its mana cost.");

			SetGenerator affected = Intersect.instance(HasType.instance(Type.INSTANT, Type.SORCERY), ControlledBy.instance(You.instance(), Stack.instance()));

			ContinuousEffect.Part part = new ContinuousEffect.Part(ContinuousEffectType.GRANT_COSTED_KEYWORD);
			part.parameters.put(ContinuousEffectType.Parameter.ABILITY, Identity.instance(org.rnd.jmagic.abilities.keywords.Replicate.class));
			part.parameters.put(ContinuousEffectType.Parameter.OBJECT, affected);
			this.addEffectPart(part);
		}
	}

	public DjinnIlluminatus(GameState state)
	{
		super(state);

		this.setPower(3);
		this.setToughness(5);

		this.addAbility(new org.rnd.jmagic.abilities.keywords.Flying(state));

		this.addAbility(new Replicant(state));
	}
}
