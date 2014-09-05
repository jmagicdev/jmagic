package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.expansions.*;

@Name("Purge the Profane")
@Types({Type.SORCERY})
@ManaCost("2WB")
@Printings({@Printings.Printed(ex = Gatecrash.class, r = Rarity.COMMON)})
@ColorIdentity({Color.WHITE, Color.BLACK})
public final class PurgetheProfane extends Card
{
	public PurgetheProfane(GameState state)
	{
		super(state);

		// Target opponent discards two cards and you gain 2 life.
		SetGenerator target = targetedBy(this.addTarget(OpponentsOf.instance(You.instance()), "target opponent"));
		this.addEffect(discardCards(target, 2, "Target opponent discards two cards"));
		this.addEffect(gainLife(You.instance(), 2, "and you gain 2 life."));
	}
}
