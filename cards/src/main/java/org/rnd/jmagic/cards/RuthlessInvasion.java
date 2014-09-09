package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Ruthless Invasion")
@Types({Type.SORCERY})
@ManaCost("3(R/P)")
@ColorIdentity({Color.RED})
public final class RuthlessInvasion extends Card
{
	public RuthlessInvasion(GameState state)
	{
		super(state);

		// Nonartifact creatures can't block this turn.
		SetGenerator artifactCreatures = Intersect.instance(HasType.instance(Type.ARTIFACT), CreaturePermanents.instance());
		SetGenerator artifactCreaturesBlocking = RelativeComplement.instance(Blocking.instance(), artifactCreatures);
		ContinuousEffect.Part part = new ContinuousEffect.Part(ContinuousEffectType.BLOCKING_RESTRICTION);
		part.parameters.put(ContinuousEffectType.Parameter.RESTRICTION, Identity.instance(artifactCreaturesBlocking));
		this.addEffect(createFloatingEffect("Nonartifact creatures can't block this turn.", part));
	}
}
