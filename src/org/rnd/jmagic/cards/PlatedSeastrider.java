package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;

@Name("Plated Seastrider")
@Types({Type.CREATURE})
@SubTypes({SubType.BEAST})
@ManaCost("UU")
@Printings({@Printings.Printed(ex = Expansion.SCARS_OF_MIRRODIN, r = Rarity.COMMON)})
@ColorIdentity({Color.BLUE})
public final class PlatedSeastrider extends Card
{
	public PlatedSeastrider(GameState state)
	{
		super(state);

		this.setPower(1);
		this.setToughness(4);
	}
}
