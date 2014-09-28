package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;

@Name("Mystic Monastery")
@Types({Type.LAND})
@ColorIdentity({Color.RED, Color.WHITE, Color.BLUE})
public final class MysticMonastery extends Card
{
	public MysticMonastery(GameState state)
	{
		super(state);

		// Mystic Monastery enters the battlefield tapped.
		this.addAbility(new org.rnd.jmagic.abilities.EntersTheBattlefieldTapped(state, this.getName()));

		// (T): Add (U), (R), or (W) to your mana pool.
		this.addAbility(new org.rnd.jmagic.abilities.TapForMana.Final(state, "(URW)"));
	}
}
