package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Dead")
@Types({Type.INSTANT})
@ManaCost("R")
@ColorIdentity({Color.RED})
public final class Dead extends Card
{
	public Dead(GameState state)
	{
		super(state);

		// Dead deals 2 damage to target creature.
		Target target = this.addTarget(CreaturePermanents.instance(), "target creature");
		this.addEffect(spellDealDamage(2, targetedBy(target), "Dead deals 2 damage to target creature."));
	}
}
