package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;

@Name("Bolt of Keranos")
@Types({Type.SORCERY})
@ManaCost("1RR")
@ColorIdentity({Color.RED})
public final class BoltofKeranos extends Card
{
	public BoltofKeranos(GameState state)
	{
		super(state);

		// Bolt of Keranos deals 3 damage to target creature or player.
		Target target = this.addTarget(CREATURES_AND_PLAYERS, "target creature or player");
		this.addEffect(spellDealDamage(3, targetedBy(target), "Bolt of Keranos deals 3 damage to target creature or player."));

		// Scry 1. (Look at the top card of your library. You may put that card
		// on the bottom of your library.)
		this.addEffect(scry(1, "Scry 1."));
	}
}
