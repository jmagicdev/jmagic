package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Culling Mark")
@Types({Type.SORCERY})
@ManaCost("2G")
@ColorIdentity({Color.GREEN})
public final class CullingMark extends Card
{
	public CullingMark(GameState state)
	{
		super(state);

		// Target creature blocks this turn if able.
		SetGenerator target = targetedBy(this.addTarget(CreaturePermanents.instance(), "target creature"));

		ContinuousEffect.Part part = new ContinuousEffect.Part(ContinuousEffectType.BLOCKING_REQUIREMENT);
		part.parameters.put(ContinuousEffectType.Parameter.DEFENDING, target);
		this.addEffect(createFloatingEffect("Target creature blocks this turn if able.", part));

		// copy-pasta from Courtly Provocateur
	}
}
