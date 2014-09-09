package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Fuel for the Cause")
@Types({Type.INSTANT})
@ManaCost("2UU")
@ColorIdentity({Color.BLUE})
public final class FuelfortheCause extends Card
{
	public FuelfortheCause(GameState state)
	{
		super(state);

		SetGenerator target = targetedBy(this.addTarget(Spells.instance(), "target spell"));

		// Counter target spell, then proliferate. (You choose any number of
		// permanents and/or players with counters on them, then give each
		// another counter of a kind already there.)
		this.addEffect(counter(target, "Counter target spell,"));
		this.addEffect(proliferate(You.instance(), "then proliferate."));
	}
}
