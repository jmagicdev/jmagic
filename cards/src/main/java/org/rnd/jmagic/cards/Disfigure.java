package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Disfigure")
@Types({Type.INSTANT})
@ManaCost("B")
@ColorIdentity({Color.BLACK})
public final class Disfigure extends Card
{
	public Disfigure(GameState state)
	{
		super(state);

		// Target creature gets -2/-2 until end of turn.
		Target target = this.addTarget(CreaturePermanents.instance(), "target creature");
		this.addEffect(ptChangeUntilEndOfTurn(targetedBy(target), (-2), (-2), "Target creature gets -2/-2 until end of turn."));
	}
}
