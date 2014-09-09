package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("March of the Machines")
@Types({Type.ENCHANTMENT})
@ManaCost("3U")
@ColorIdentity({Color.BLUE})
public final class MarchoftheMachines extends Card
{
	public static final class March extends StaticAbility
	{
		public March(GameState state)
		{
			super(state, "Each noncreature artifact is an artifact creature with power and toughness each equal to its converted mana cost.");

			SetGenerator artifacts = ArtifactPermanents.instance();
			SetGenerator creatures = CreaturePermanents.instance();
			SetGenerator nonCreatureArtifacts = RelativeComplement.instance(artifacts, creatures);

			ContinuousEffect.Part animate = new ContinuousEffect.Part(ContinuousEffectType.ADD_TYPES);
			animate.parameters.put(ContinuousEffectType.Parameter.OBJECT, nonCreatureArtifacts);
			animate.parameters.put(ContinuousEffectType.Parameter.TYPE, Identity.instance(Type.CREATURE));
			this.addEffectPart(animate);

			ContinuousEffect.Part empower = new ContinuousEffect.Part(ContinuousEffectType.SET_PT_TO_CMC);
			empower.parameters.put(ContinuousEffectType.Parameter.OBJECT, nonCreatureArtifacts);
			this.addEffectPart(empower);
		}
	}

	public MarchoftheMachines(GameState state)
	{
		super(state);

		this.addAbility(new March(state));
	}
}
