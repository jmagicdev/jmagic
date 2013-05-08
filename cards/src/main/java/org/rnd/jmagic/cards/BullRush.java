package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Bull Rush")
@Types({Type.INSTANT})
@ManaCost("R")
@Printings({@Printings.Printed(ex = Expansion.WORLDWAKE, r = Rarity.COMMON)})
@ColorIdentity({Color.RED})
public final class BullRush extends Card
{
	public BullRush(GameState state)
	{
		super(state);

		// Target creature gets +2/+0 until end of turn.
		Target t = this.addTarget(CreaturePermanents.instance(), "target creature");
		this.addEffect(ptChangeUntilEndOfTurn(targetedBy(t), +2, +0, "Target creature gets +2/+0 until end of turn."));
	}
}
