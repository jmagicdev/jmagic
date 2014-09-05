package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.expansions.*;

@Name("One with Nothing")
@Types({Type.INSTANT})
@ManaCost("B")
@Printings({@Printings.Printed(ex = SaviorsOfKamigawa.class, r = Rarity.RARE)})
@ColorIdentity({Color.BLACK})
public final class OnewithNothing extends Card
{
	public OnewithNothing(GameState state)
	{
		super(state);

		this.addEffect(discardHand(You.instance(), "Discard your hand."));
	}
}
