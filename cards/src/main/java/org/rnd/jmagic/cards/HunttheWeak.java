package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Hunt the Weak")
@Types({Type.SORCERY})
@ManaCost("3G")
@ColorIdentity({Color.GREEN})
public final class HunttheWeak extends Card
{
	public HunttheWeak(GameState state)
	{
		super(state);

		// Put a +1/+1 counter on target creature you control.
		SetGenerator yours = targetedBy(this.addTarget(CREATURES_YOU_CONTROL, "target creature you control"));
		this.addEffect(putCounters(1, Counter.CounterType.PLUS_ONE_PLUS_ONE, yours, "Put a +1/+1 counter on target creature you control."));

		// Then that creature fights target creature you don't control. (Each
		// deals damage equal to its power to the other.)
		SetGenerator theirs = targetedBy(this.addTarget(RelativeComplement.instance(CreaturePermanents.instance(), ControlledBy.instance(You.instance())), "target creature you don't control"));
		this.addEffect(fight(Union.instance(yours, theirs), "Then that creature fights target creature you don't control."));
	}
}
