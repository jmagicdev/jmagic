package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Mirrorweave")
@Types({Type.INSTANT})
@ManaCost("2(W/U)(W/U)")
@ColorIdentity({Color.WHITE, Color.BLUE})
public final class Mirrorweave extends Card
{
	public Mirrorweave(GameState state)
	{
		super(state);

		Target target = this.addTarget(RelativeComplement.instance(CreaturePermanents.instance(), HasSuperType.instance(SuperType.LEGENDARY)), "target nonlegendary creature");

		// Each other creature becomes a copy of target nonlegendary creature
		// until end of turn.
		ContinuousEffect.Part part = new ContinuousEffect.Part(ContinuousEffectType.COPY_OBJECT);
		part.parameters.put(ContinuousEffectType.Parameter.ORIGINAL, targetedBy(target));
		part.parameters.put(ContinuousEffectType.Parameter.OBJECT, RelativeComplement.instance(CreaturePermanents.instance(), targetedBy(target)));
		this.addEffect(createFloatingEffect("Each other creature becomes a copy of target nonlegendary creature until end of turn.", part));
	}
}
