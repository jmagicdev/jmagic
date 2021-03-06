package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;

@Name("Wanderwine Hub")
@Types({Type.LAND})
@ColorIdentity({Color.WHITE, Color.BLUE})
public final class WanderwineHub extends Card
{
	public WanderwineHub(GameState state)
	{
		super(state);

		// As Wanderwine Hub enters the battlefield, you may reveal a Merfolk
		// card from your hand. If you don't, Wanderwine Hub enters the
		// battlefield tapped.
		this.addAbility(new org.rnd.jmagic.abilities.RevealOrThisEntersTapped(state, this.getName(), SubType.MERFOLK));

		// (T): Add (W) or (U) to your mana pool.
		this.addAbility(new org.rnd.jmagic.abilities.TapForMana.Final(state, "(WU)"));
	}
}
