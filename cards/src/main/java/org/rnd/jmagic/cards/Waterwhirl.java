package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Waterwhirl")
@Types({Type.INSTANT})
@ManaCost("4UU")
@ColorIdentity({Color.BLUE})
public final class Waterwhirl extends Card
{
	public Waterwhirl(GameState state)
	{
		super(state);

		// Return up to two target creatures to their owners' hands.
		SetGenerator target = targetedBy(this.addTarget(CreaturePermanents.instance(), "up to two target creatures").setNumber(0, 2));
		this.addEffect(bounce(target, "Return up to two target creatures to their owners' hands."));
	}
}
