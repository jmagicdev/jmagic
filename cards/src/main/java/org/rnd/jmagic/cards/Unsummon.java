package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Unsummon")
@Types({Type.INSTANT})
@ManaCost("U")
@ColorIdentity({Color.BLUE})
public final class Unsummon extends Card
{
	public Unsummon(GameState state)
	{
		super(state);

		// Return target creature to its owner's hand.
		Target target = this.addTarget(CreaturePermanents.instance(), "target creature");
		this.addEffect(bounce(targetedBy(target), "Return target creature to its owner's hand."));
	}
}
