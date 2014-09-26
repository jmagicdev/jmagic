package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Lens of Clarity")
@Types({Type.ARTIFACT})
@ManaCost("1")
@ColorIdentity({})
public final class LensofClarity extends Card
{
	public static final class LensofClarityAbility0 extends StaticAbility
	{
		public LensofClarityAbility0(GameState state)
		{
			super(state, "You may look at the top card of your library and at face-down creatures you don't control.");

			ContinuousEffect.Part lookAtTop = new ContinuousEffect.Part(ContinuousEffectType.LOOK);
			lookAtTop.parameters.put(ContinuousEffectType.Parameter.PLAYER, You.instance());
			lookAtTop.parameters.put(ContinuousEffectType.Parameter.OBJECT, TopCards.instance(1, LibraryOf.instance(You.instance())));
			this.addEffectPart(lookAtTop);

			SetGenerator morphs = Intersect.instance(FaceDown.instance(), CreaturePermanents.instance());
			SetGenerator otherPlayersMorphs = RelativeComplement.instance(morphs, ControlledBy.instance(You.instance()));

			ContinuousEffect.Part lookAtMorphs = new ContinuousEffect.Part(ContinuousEffectType.LOOK_PHYSICALLY);
			lookAtMorphs.parameters.put(ContinuousEffectType.Parameter.PLAYER, You.instance());
			lookAtMorphs.parameters.put(ContinuousEffectType.Parameter.OBJECT, otherPlayersMorphs);
			this.addEffectPart(lookAtMorphs);
		}
	}

	public LensofClarity(GameState state)
	{
		super(state);

		// You may look at the top card of your library and at face-down
		// creatures you don't control. (You may do this at any time.)
		this.addAbility(new LensofClarityAbility0(state));
	}
}
