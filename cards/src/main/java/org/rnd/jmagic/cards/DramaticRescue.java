package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Dramatic Rescue")
@Types({Type.INSTANT})
@ManaCost("WU")
@ColorIdentity({Color.WHITE, Color.BLUE})
public final class DramaticRescue extends Card
{
	public DramaticRescue(GameState state)
	{
		super(state);

		// Return target creature to its owner's hand. You gain 2 life.
		SetGenerator target = targetedBy(this.addTarget(CreaturePermanents.instance(), "target creature"));
		this.addEffect(bounce(target, "Return target creature to its owner's hand."));
		this.addEffect(gainLife(You.instance(), 2, "You gain 2 life."));
	}
}
