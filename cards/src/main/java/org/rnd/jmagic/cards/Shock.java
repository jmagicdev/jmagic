package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;

@Name("Shock")
@Types({Type.INSTANT})
@ManaCost("R")
@ColorIdentity({Color.RED})
public final class Shock extends Card
{
	public Shock(GameState state)
	{
		super(state);

		Target target = this.addTarget(CREATURES_AND_PLAYERS, "target creature or player");
		this.addEffect(spellDealDamage(2, targetedBy(target), "Shock deals 2 damage to target creature or player."));
	}
}
