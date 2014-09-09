package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;

@Name("Mox Jet")
@Types({Type.ARTIFACT})
@ManaCost("0")
@ColorIdentity({Color.BLACK})
public final class MoxJet extends Card
{
	public MoxJet(GameState state)
	{
		super(state);

		// (T): Add (B) to your mana pool.
		this.addAbility(new org.rnd.jmagic.abilities.TapForB(state));
	}
}
