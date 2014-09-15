package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Pillar of War")
@Types({Type.ARTIFACT, Type.CREATURE})
@SubTypes({SubType.GOLEM})
@ManaCost("3")
@ColorIdentity({})
public final class PillarofWar extends Card
{
	public static final class PillarofWarAbility1 extends StaticAbility
	{
		public PillarofWarAbility1(GameState state)
		{
			super(state, "As long as Pillar of War is enchanted, it can attack as though it didn't have defender.");

			SetGenerator isEnchanted = Intersect.instance(AttachedTo.instance(This.instance()), HasSubType.instance(SubType.AURA));
			this.canApply = Both.instance(this.canApply, isEnchanted);

			ContinuousEffect.Part part = new ContinuousEffect.Part(ContinuousEffectType.ATTACK_AS_THOUGH_DOESNT_HAVE_DEFENDER);
			part.parameters.put(ContinuousEffectType.Parameter.OBJECT, This.instance());
			this.addEffectPart(part);
		}
	}

	public PillarofWar(GameState state)
	{
		super(state);

		this.setPower(3);
		this.setToughness(3);

		// Defender
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Defender(state));

		// As long as Pillar of War is enchanted, it can attack as though it
		// didn't have defender.
		this.addAbility(new PillarofWarAbility1(state));
	}
}
