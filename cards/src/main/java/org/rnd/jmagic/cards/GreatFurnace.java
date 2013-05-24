package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;

@Name("Great Furnace")
@Types({Type.ARTIFACT, Type.LAND})
@Printings({@Printings.Printed(ex = Expansion.MIRRODIN, r = Rarity.COMMON)})
@ColorIdentity({Color.RED})
public final class GreatFurnace extends Card
{
	public GreatFurnace(GameState state)
	{
		super(state);

		this.addAbility(new org.rnd.jmagic.abilities.TapForR(state));
	}
}
