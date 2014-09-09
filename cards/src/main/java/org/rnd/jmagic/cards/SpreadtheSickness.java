package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Spread the Sickness")
@Types({Type.SORCERY})
@ManaCost("4B")
@ColorIdentity({Color.BLACK})
public final class SpreadtheSickness extends Card
{
	public SpreadtheSickness(GameState state)
	{
		super(state);

		SetGenerator target = targetedBy(this.addTarget(CreaturePermanents.instance(), "target creature"));

		// Destroy target creature, then proliferate. (You choose any number of
		// permanents and/or players with counters on them, then give each
		// another counter of a kind already there.)
		this.addEffect(destroy(target, "Destroy target creature,"));
		this.addEffect(proliferate(You.instance(), "then proliferate."));
	}
}
