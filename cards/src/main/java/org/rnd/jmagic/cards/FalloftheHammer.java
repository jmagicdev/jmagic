package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Fall of the Hammer")
@Types({Type.INSTANT})
@ManaCost("1R")
@ColorIdentity({Color.RED})
public final class FalloftheHammer extends Card
{
	public FalloftheHammer(GameState state)
	{
		super(state);

		// Target creature you control deals damage equal to its power to
		// another target creature.
		Target yourTarget = this.addTarget(CREATURES_YOU_CONTROL, "target creature you control");
		yourTarget.restrictFromLaterTargets = true;
		SetGenerator goodGuy = targetedBy(yourTarget);
		SetGenerator badGuy = targetedBy(this.addTarget(CreaturePermanents.instance(), "target creature"));

		EventFactory damage = new EventFactory(EventType.DEAL_DAMAGE_EVENLY, "Target creature you control deals damage equal to its power to another target creature.");
		damage.parameters.put(EventType.Parameter.SOURCE, goodGuy);
		damage.parameters.put(EventType.Parameter.TAKER, badGuy);
		damage.parameters.put(EventType.Parameter.NUMBER, PowerOf.instance(goodGuy));
		this.addEffect(damage);
	}
}
