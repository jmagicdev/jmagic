package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Skull Rend")
@Types({Type.SORCERY})
@ManaCost("3BR")
@ColorIdentity({Color.BLACK, Color.RED})
public final class SkullRend extends Card
{
	public SkullRend(GameState state)
	{
		super(state);

		// Skull Rend deals 2 damage to each opponent. Those players each
		// discard two cards at random.
		OpponentsOf eachOpponent = OpponentsOf.instance(You.instance());
		this.addEffect(spellDealDamage(2, eachOpponent, "Skull Rend deals 2 damage to each opponent."));
		this.addEffect(discardRandom(eachOpponent, 2, "Those players each discard two cards at random."));
	}
}
