package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.expansions.*;

@Name("Blood Crypt")
@Types({Type.LAND})
@SubTypes({SubType.SWAMP, SubType.MOUNTAIN})
@Printings({@Printings.Printed(ex = ReturnToRavnica.class, r = Rarity.RARE), @Printings.Printed(ex = Dissension.class, r = Rarity.RARE)})
@ColorIdentity({Color.BLACK, Color.RED})
public final class BloodCrypt extends Card
{
	public BloodCrypt(GameState state)
	{
		super(state);

		this.addAbility(new org.rnd.jmagic.abilities.RavnicaShockLandAbility(state, this.getName()));
	}
}
