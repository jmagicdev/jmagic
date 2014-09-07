package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.expansions.*;

@Name("Gone")
@Types({Type.INSTANT})
@ManaCost("2R")
@Printings({@Printings.Printed(ex = PlanarChaos.class, r = Rarity.COMMON)})
@ColorIdentity({Color.RED})
public final class Gone extends Card
{
	public Gone(GameState state)
	{
		super(state);

		// Return target creature you don't control to its owner's hand.
		SetGenerator creatureYouDontControl = RelativeComplement.instance(CreaturePermanents.instance(), ControlledBy.instance(You.instance()));
		SetGenerator target = targetedBy(this.addTarget(creatureYouDontControl, "target creature you don't control"));
		this.addEffect(bounce(target, "Return target creature you don't control to its owner's hand."));
	}
}
