package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Vapor Snag")
@Types({Type.INSTANT})
@ManaCost("U")
@ColorIdentity({Color.BLUE})
public final class VaporSnag extends Card
{
	public VaporSnag(GameState state)
	{
		super(state);

		// Return target creature to its owner's hand. Its controller loses 1
		// life.
		SetGenerator target = targetedBy(this.addTarget(CreaturePermanents.instance(), "target creature"));
		this.addEffect(bounce(target, "Return target creature to its owner's hand."));
		this.addEffect(loseLife(ControllerOf.instance(target), 1, "Its controller loses 1 life."));
	}
}
