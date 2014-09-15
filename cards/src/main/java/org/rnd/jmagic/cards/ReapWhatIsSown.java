package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Reap What Is Sown")
@Types({Type.INSTANT})
@ManaCost("1GW")
@ColorIdentity({Color.WHITE, Color.GREEN})
public final class ReapWhatIsSown extends Card
{
	public ReapWhatIsSown(GameState state)
	{
		super(state);

		// Put a +1/+1 counter on each of up to three target creatures.
		SetGenerator target = targetedBy(this.addTarget(CreaturePermanents.instance(), "up to three target creatures").setNumber(3, 3));
		this.addEffect(putCounters(1, Counter.CounterType.PLUS_ONE_PLUS_ONE, target, "Put a +1/+1 counter on each of up to three target creatures."));
	}
}
