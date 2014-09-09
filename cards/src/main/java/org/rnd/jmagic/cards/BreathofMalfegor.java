package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Breath of Malfegor")
@Types({Type.INSTANT})
@ManaCost("3BR")
@ColorIdentity({Color.BLACK, Color.RED})
public final class BreathofMalfegor extends Card
{
	public BreathofMalfegor(GameState state)
	{
		super(state);

		this.addEffect(spellDealDamage(5, OpponentsOf.instance(You.instance()), "Breath of Malfegor deals 5 damage to each opponent."));
	}
}
