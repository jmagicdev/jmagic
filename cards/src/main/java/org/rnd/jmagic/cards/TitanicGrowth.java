package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Titanic Growth")
@Types({Type.INSTANT})
@ManaCost("1G")
@ColorIdentity({Color.GREEN})
public final class TitanicGrowth extends Card
{
	public TitanicGrowth(GameState state)
	{
		super(state);

		// Target creature gets +4/+4 until end of turn.
		Target target = this.addTarget(CreaturePermanents.instance(), "target creature");
		this.addEffect(ptChangeUntilEndOfTurn(targetedBy(target), +4, +4, "Target creature gets +4/+4 until end of turn."));
	}
}
