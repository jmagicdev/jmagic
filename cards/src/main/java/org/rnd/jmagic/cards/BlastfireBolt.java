package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Blastfire Bolt")
@Types({Type.INSTANT})
@ManaCost("5R")
@ColorIdentity({Color.RED})
public final class BlastfireBolt extends Card
{
	public BlastfireBolt(GameState state)
	{
		super(state);

		// Blastfire Bolt deals 5 damage to target creature.
		SetGenerator target = targetedBy(this.addTarget(CreaturePermanents.instance(), "target creature"));
		this.addEffect(spellDealDamage(5, target, "Blastfire Bolt deals 5 damage to target creature."));

		// Destroy all Equipment attached to that creature.
		SetGenerator equipment = Intersect.instance(HasSubType.instance(SubType.EQUIPMENT), AttachedTo.instance(target));
		this.addEffect(destroy(equipment, "Destroy all Equipment attached to that creature."));
	}
}
