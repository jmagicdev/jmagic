package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Puncturing Light")
@Types({Type.INSTANT})
@ManaCost("1W")
@ColorIdentity({Color.WHITE})
public final class PuncturingLight extends Card
{
	public PuncturingLight(GameState state)
	{
		super(state);

		// Destroy target attacking or blocking creature with power 3 or less.
		SetGenerator inCombat = Union.instance(Attacking.instance(), Blocking.instance());
		SetGenerator withPower3OrLess = Intersect.instance(inCombat, HasPower.instance(Between.instance(null, 3)));
		SetGenerator target = targetedBy(this.addTarget(withPower3OrLess, "target attacking or blocking creature with power 3 or less"));
		this.addEffect(destroy(target, "Destroy target attacking or blocking creature with power 3 or less."));
	}
}
