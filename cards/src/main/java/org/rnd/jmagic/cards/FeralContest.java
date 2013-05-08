package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Feral Contest")
@Types({Type.SORCERY})
@ManaCost("3G")
@Printings({@Printings.Printed(ex = Expansion.WORLDWAKE, r = Rarity.COMMON)})
@ColorIdentity({Color.GREEN})
public final class FeralContest extends Card
{
	public FeralContest(GameState state)
	{
		super(state);

		Target firstTarget = this.addTarget(CREATURES_YOU_CONTROL, "target creature you control");
		firstTarget.restrictFromLaterTargets = true;
		SetGenerator getsCounter = targetedBy(firstTarget);
		SetGenerator blocks = targetedBy(this.addTarget(CreaturePermanents.instance(), "another target creature"));

		// Put a +1/+1 counter on target creature you control. Another target
		// creature blocks it this turn if able.
		this.addEffect(putCounters(1, Counter.CounterType.PLUS_ONE_PLUS_ONE, getsCounter, "Put a +1/+1 counter on target creature you control."));

		ContinuousEffect.Part part = new ContinuousEffect.Part(ContinuousEffectType.BLOCKING_REQUIREMENT);
		part.parameters.put(ContinuousEffectType.Parameter.ATTACKING, getsCounter);
		part.parameters.put(ContinuousEffectType.Parameter.DEFENDING, blocks);
		this.addEffect(createFloatingEffect("Another target creature blocks it this turn if able.", part));
	}
}
