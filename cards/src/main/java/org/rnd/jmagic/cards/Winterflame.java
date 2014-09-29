package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Winterflame")
@Types({Type.INSTANT})
@ManaCost("1UR")
@ColorIdentity({Color.RED, Color.BLUE})
public final class Winterflame extends Card
{
	public Winterflame(GameState state)
	{
		super(state);

		// Choose one or both \u2014

		// • Tap target creature.
		{
			SetGenerator target = targetedBy(this.addTarget(1, CreaturePermanents.instance(), "target creature"));
			this.addEffect(1, tap(target, "Tap target creature."));
		}

		// • Winterflame deals 2 damage to target creature.
		{
			SetGenerator target = targetedBy(this.addTarget(2, CreaturePermanents.instance(), "target creature"));
			this.addEffect(2, spellDealDamage(2, target, "Winterflame deals 2 damage to target creature."));
		}
	}
}
