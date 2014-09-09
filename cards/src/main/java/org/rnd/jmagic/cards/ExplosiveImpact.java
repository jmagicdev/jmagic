package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;

@Name("Explosive Impact")
@Types({Type.INSTANT})
@ManaCost("5R")
@ColorIdentity({Color.RED})
public final class ExplosiveImpact extends Card
{
	public ExplosiveImpact(GameState state)
	{
		super(state);

		// Explosive Impact deals 5 damage to target creature or player.
		SetGenerator target = targetedBy(this.addTarget(CREATURES_AND_PLAYERS, "target creature or player"));
		this.addEffect(spellDealDamage(5, target, "Explosive Impact deals 5 damage to target creature or player."));
	}
}
