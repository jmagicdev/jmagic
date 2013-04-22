package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;

@Name("Seat of the Synod")
@Types({Type.LAND, Type.ARTIFACT})
@Printings({@Printings.Printed(ex = Expansion.MIRRODIN, r = Rarity.COMMON)})
@ColorIdentity({Color.BLUE})
public final class SeatoftheSynod extends Card
{
	public SeatoftheSynod(GameState state)
	{
		super(state);

		this.addAbility(new org.rnd.jmagic.abilities.TapForU(state));
	}
}
