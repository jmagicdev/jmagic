package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.expansions.*;

@Name("\u00C6ther Tradewinds")
@Types({Type.INSTANT})
@ManaCost("2U")
@Printings({@Printings.Printed(ex = Worldwake.class, r = Rarity.COMMON)})
@ColorIdentity({Color.BLUE})
public final class AEtherTradewinds extends Card
{
	public AEtherTradewinds(GameState state)
	{
		super(state);

		// Return target permanent you control and target permanent you don't
		// control to their owners' hands.
		Target target1 = this.addTarget(ControlledBy.instance(You.instance()), "target permanent you control");
		Target target2 = this.addTarget(ControlledBy.instance(RelativeComplement.instance(Players.instance(), You.instance())), "target permanent you don't control");
		this.addEffect(bounce(targetedBy(target1, target2), "Return target permanent you control and target permanent you don't control to their owners' hands."));
	}
}
