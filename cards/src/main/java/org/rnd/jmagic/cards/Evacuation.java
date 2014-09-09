package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Evacuation")
@Types({Type.INSTANT})
@ManaCost("3UU")
@ColorIdentity({Color.BLUE})
public final class Evacuation extends Card
{
	public Evacuation(GameState state)
	{
		super(state);

		this.addEffect(bounce(CreaturePermanents.instance(), "Return all creatures to their owners' hands."));
	}
}
