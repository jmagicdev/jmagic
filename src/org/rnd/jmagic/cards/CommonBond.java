package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Common Bond")
@Types({Type.INSTANT})
@ManaCost("1GW")
@Printings({@Printings.Printed(ex = Expansion.RETURN_TO_RAVNICA, r = Rarity.COMMON)})
@ColorIdentity({Color.WHITE, Color.GREEN})
public final class CommonBond extends Card
{
	public CommonBond(GameState state)
	{
		super(state);

		// Put a +1/+1 counter on target creature. Put a +1/+1 counter on target
		// creature.

		for(int i = 0; i < 2; ++i)
		{
			SetGenerator target = targetedBy(this.addTarget(CreaturePermanents.instance(), "target creature"));
			this.addEffect(putCounters(1, Counter.CounterType.PLUS_ONE_PLUS_ONE, target, "Put a +1/+1 counter on target creature."));
		}
	}
}
