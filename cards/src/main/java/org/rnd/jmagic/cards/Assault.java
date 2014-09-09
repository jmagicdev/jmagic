package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;

@Name("Assault")
@Types({Type.SORCERY})
@ManaCost("R")
@ColorIdentity({Color.RED})
public final class Assault extends Card
{
	public Assault(GameState state)
	{
		super(state);

		// Assault deals 2 damage to target creature or player.
		Target target = this.addTarget(CREATURES_AND_PLAYERS, "target creature or player");
		this.addEffect(spellDealDamage(2, targetedBy(target), "Assault deals 2 damage to target creature or player."));
	}
}
