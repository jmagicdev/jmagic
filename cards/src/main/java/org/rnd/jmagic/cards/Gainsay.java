package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Gainsay")
@Types({Type.INSTANT})
@ManaCost("1U")
@ColorIdentity({Color.BLUE})
public final class Gainsay extends Card
{
	public Gainsay(GameState state)
	{
		super(state);

		// Counter target blue spell.
		Target target = this.addTarget(Intersect.instance(HasColor.instance(Color.BLUE), Spells.instance()), "target blue spell");

		this.addEffect(counter(targetedBy(target), "Counter target spell."));
	}
}
