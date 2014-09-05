package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.expansions.*;

@Name("Mark for Death")
@Types({Type.SORCERY})
@ManaCost("3R")
@Printings({@Printings.Printed(ex = Gatecrash.class, r = Rarity.UNCOMMON)})
@ColorIdentity({Color.RED})
public final class MarkforDeath extends Card
{
	public MarkforDeath(GameState state)
	{
		super(state);

		// Target creature an opponent controls blocks this turn if able. Untap
		// that creature. Other creatures that player controls can't block this
		// turn.
		SetGenerator target = targetedBy(this.addTarget(Intersect.instance(CreaturePermanents.instance(), ControlledBy.instance(OpponentsOf.instance(You.instance()))), "target creature an opponent controls"));

		ContinuousEffect.Part part1 = new ContinuousEffect.Part(ContinuousEffectType.BLOCKING_REQUIREMENT);
		part1.parameters.put(ContinuousEffectType.Parameter.DEFENDING, target);

		ContinuousEffect.Part part2 = new ContinuousEffect.Part(ContinuousEffectType.BLOCKING_RESTRICTION);
		part2.parameters.put(ContinuousEffectType.Parameter.RESTRICTION, Identity.instance(Intersect.instance(Blocking.instance(), RelativeComplement.instance(Intersect.instance(ControlledBy.instance(ControllerOf.instance(target)), CreaturePermanents.instance()), target))));

		this.addEffect(createFloatingEffect("Target creature an opponent controls blocks this turn if able. Other creatures that player controls can't block this turn.", part1, part2));
	}
}
