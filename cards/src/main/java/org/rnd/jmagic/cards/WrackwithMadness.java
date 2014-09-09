package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Wrack with Madness")
@Types({Type.SORCERY})
@ManaCost("3R")
@ColorIdentity(Color.RED)
public final class WrackwithMadness extends Card
{
	public WrackwithMadness(GameState state)
	{
		super(state);

		// Target creature deals damage to itself equal to its power.
		SetGenerator target = targetedBy(this.addTarget(CreaturePermanents.instance(), "target creature"));
		EventFactory damage = new EventFactory(EventType.DEAL_DAMAGE_EVENLY, "Target creature deals damage to itself equal to its power.");
		damage.parameters.put(EventType.Parameter.SOURCE, target);
		damage.parameters.put(EventType.Parameter.NUMBER, PowerOf.instance(target));
		damage.parameters.put(EventType.Parameter.TAKER, target);
		this.addEffect(damage);
	}
}
