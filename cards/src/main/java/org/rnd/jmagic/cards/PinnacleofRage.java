package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;

@Name("Pinnacle of Rage")
@Types({Type.SORCERY})
@ManaCost("4RR")
@ColorIdentity({Color.RED})
public final class PinnacleofRage extends Card
{
	public PinnacleofRage(GameState state)
	{
		super(state);

		// Pinnacle of Rage deals 3 damage to each of two target creatures
		// and/or players.
		SetGenerator target = targetedBy(this.addTarget(CREATURES_AND_PLAYERS, "two target creatures and/or players").setNumber(2, 2));
		this.addEffect(spellDealDamage(3, target, "Pinnacle of Rage deals 3 damage to each of two target creatures and/or players."));
	}
}
