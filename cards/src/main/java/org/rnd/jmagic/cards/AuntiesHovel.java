package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.expansions.*;

@Name("Auntie's Hovel")
@Types({Type.LAND})
@Printings({@Printings.Printed(ex = Lorwyn.class, r = Rarity.RARE)})
@ColorIdentity({Color.BLACK, Color.RED})
public final class AuntiesHovel extends Card
{
	public AuntiesHovel(GameState state)
	{
		super(state);

		this.addAbility(new org.rnd.jmagic.abilities.RevealOrThisEntersTapped(state, this.getName(), SubType.GOBLIN));
		this.addAbility(new org.rnd.jmagic.abilities.TapForMana.Final(state, "(BR)"));
	}
}
