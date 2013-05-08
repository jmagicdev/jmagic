package org.rnd.jmagic.abilities;

import org.rnd.jmagic.engine.*;

public final class TapForAnyColor extends org.rnd.jmagic.abilities.TapForMana
{
	public TapForAnyColor(GameState state)
	{
		super(state, "(WUBRG)");

		this.setName("(T): Add one mana of any color to your mana pool.");
	}
}
