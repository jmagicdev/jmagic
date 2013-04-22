package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Narrow Escape")
@Types({Type.INSTANT})
@ManaCost("2W")
@Printings({@Printings.Printed(ex = Expansion.ZENDIKAR, r = Rarity.COMMON)})
@ColorIdentity({Color.WHITE})
public final class NarrowEscape extends Card
{
	public NarrowEscape(GameState state)
	{
		super(state);

		Target target = this.addTarget(ControlledBy.instance(You.instance()), "target permanent you control");

		this.addEffect(bounce(targetedBy(target), "Return target permanent you control to its owner's hand."));

		this.addEffect(gainLife(You.instance(), 4, "You gain 4 life."));
	}
}
