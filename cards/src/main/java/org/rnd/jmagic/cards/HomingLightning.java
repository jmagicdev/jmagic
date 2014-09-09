package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Homing Lightning")
@Types({Type.INSTANT})
@ManaCost("2RR")
@ColorIdentity({Color.RED})
public final class HomingLightning extends Card
{
	public HomingLightning(GameState state)
	{
		super(state);

		// Homing Lightning deals 4 damage to target creature and each other
		// creature with the same name as that creature.
		SetGenerator target = targetedBy(this.addTarget(CreaturePermanents.instance(), "target creature"));
		SetGenerator sameName = HasName.instance(NameOf.instance(target));
		SetGenerator creatures = Intersect.instance(CreaturePermanents.instance(), sameName);
		this.addEffect(spellDealDamage(4, creatures, "Homing Lightning deals 4 damage to target creature and each other creature with the same name as that creature."));
	}
}
