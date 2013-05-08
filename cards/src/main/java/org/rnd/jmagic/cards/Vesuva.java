package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Vesuva")
@Types({Type.LAND})
@Printings({@Printings.Printed(ex = Expansion.TIME_SPIRAL, r = Rarity.RARE)})
@ColorIdentity({})
public final class Vesuva extends Card
{
	public Vesuva(GameState state)
	{
		super(state);

		this.addAbility(new org.rnd.jmagic.abilities.YouMayHaveThisEnterTheBattlefieldAsACopy(LandPermanents.instance()).tapped().generateName(this.getName(), "any land on the battlefield").getStaticAbility(state));
	}
}
