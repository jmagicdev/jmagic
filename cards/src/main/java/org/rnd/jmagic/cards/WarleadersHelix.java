package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Warleader's Helix")
@Types({Type.INSTANT})
@ManaCost("2WR")
@ColorIdentity({Color.WHITE, Color.RED})
public final class WarleadersHelix extends Card
{
	public WarleadersHelix(GameState state)
	{
		super(state);

		// Warleader's Helix deals 4 damage to target creature or player and you
		// gain 4 life.
		Target target = this.addTarget(CREATURES_AND_PLAYERS, "target creature or player");
		this.addEffect(spellDealDamage(4, targetedBy(target), "Warleader's Helix deals 4 damage to target creature or player"));
		this.addEffect(gainLife(You.instance(), 4, "and you gain 4 life."));
	}
}
