package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Panic Attack")
@Types({Type.SORCERY})
@ManaCost("2R")
@ColorIdentity({Color.RED})
public final class PanicAttack extends Card
{
	public PanicAttack(GameState state)
	{
		super(state);

		Target target = this.addTarget(CreaturePermanents.instance(), "up to three target creatures");
		target.setNumber(0, 3);

		ContinuousEffect.Part part = new ContinuousEffect.Part(ContinuousEffectType.BLOCKING_RESTRICTION);
		part.parameters.put(ContinuousEffectType.Parameter.RESTRICTION, Identity.instance(Intersect.instance(Blocking.instance(), targetedBy(target))));

		this.addEffect(createFloatingEffect("Up to three target creatures can't block this turn.", part));
	}
}
