package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Time Walk")
@Types({Type.SORCERY})
@ManaCost("1U")
@ColorIdentity({Color.BLUE})
public final class TimeWalk extends Card
{
	public TimeWalk(GameState state)
	{
		super(state);

		// Take an extra turn after this one.
		this.addEffect(takeExtraTurns(You.instance(), 1, "Take an extra turn after this one."));
	}
}
