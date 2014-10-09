package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Nature's Panoply")
@Types({Type.INSTANT})
@ManaCost("G")
@ColorIdentity({Color.GREEN})
public final class NaturesPanoply extends Card
{
	public NaturesPanoply(GameState state)
	{
		super(state);

		// Strive \u2014 Nature's Panoply costs (2)(G) more to cast for each
		// target beyond the first.
		this.addAbility(new org.rnd.jmagic.abilities.Strive(state, this.getName(), "(2)(G)"));

		// Choose any number of target creatures. Put a +1/+1 counter on each of
		// them.
		SetGenerator target = targetedBy(this.addTarget(CreaturePermanents.instance(), "any number of target creatures").setNumber(0, null));
		this.addEffect(putCounters(1, Counter.CounterType.PLUS_ONE_PLUS_ONE, target, "Choose any number of target creatures. Put a +1/+1 counter on each of them."));
	}
}
