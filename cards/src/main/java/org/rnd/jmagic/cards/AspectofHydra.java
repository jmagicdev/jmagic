package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Aspect of Hydra")
@Types({Type.INSTANT})
@ManaCost("G")
@ColorIdentity({Color.GREEN})
public final class AspectofHydra extends Card
{
	public AspectofHydra(GameState state)
	{
		super(state);

		// Target creature gets +X/+X until end of turn, where X is your
		// devotion to green.
		SetGenerator target = targetedBy(this.addTarget(CreaturePermanents.instance(), "target creature"));
		SetGenerator amt = DevotionTo.instance(Color.GREEN);
		this.addEffect(ptChangeUntilEndOfTurn(target, amt, amt, "Target creature gets +X/+X until end of turn, where X is your devotion to green."));
	}
}
