package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Boomerang")
@Types({Type.INSTANT})
@ManaCost("UU")
@ColorIdentity({Color.BLUE})
public final class Boomerang extends Card
{
	public Boomerang(GameState state)
	{
		super(state);

		Target target = this.addTarget(Permanents.instance(), "target permanent");
		this.addEffect(bounce(targetedBy(target), "Return target permanent to its owner's hand."));
	}
}
