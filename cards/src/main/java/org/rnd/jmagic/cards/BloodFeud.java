package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Blood Feud")
@Types({Type.SORCERY})
@ManaCost("4RR")
@ColorIdentity({Color.RED})
public final class BloodFeud extends Card
{
	public BloodFeud(GameState state)
	{
		super(state);

		// Target creature fights another target creature. (Each deals damage
		// equal to its power to the other.)
		Target target1 = this.addTarget(CreaturePermanents.instance(), "target creature");
		target1.restrictFromLaterTargets = true;
		Target target2 = this.addTarget(CreaturePermanents.instance(), "another target creature");
		this.addEffect(fight(Union.instance(targetedBy(target1), targetedBy(target2)), "Target creature fights another target creature."));
	}
}
