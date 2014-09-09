package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;

@Name("Sol Ring")
@Types({Type.ARTIFACT})
@ManaCost("1")
@ColorIdentity({})
public final class SolRing extends Card
{
	public SolRing(GameState state)
	{
		super(state);

		// (T): Add (2) to your mana pool.
		this.addAbility(new org.rnd.jmagic.abilities.TapForMana.Final(state, "(2)"));
	}
}
