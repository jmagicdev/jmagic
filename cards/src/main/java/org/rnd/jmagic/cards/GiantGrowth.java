package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Giant Growth")
@Types({Type.INSTANT})
@ManaCost("G")
@ColorIdentity({Color.GREEN})
public final class GiantGrowth extends Card
{
	public GiantGrowth(GameState state)
	{
		super(state);

		Target target = this.addTarget(CreaturePermanents.instance(), "target creature");

		this.addEffect(ptChangeUntilEndOfTurn(targetedBy(target), (+3), (+3), "Target creature gets +3/+3 until end of turn."));
	}
}
