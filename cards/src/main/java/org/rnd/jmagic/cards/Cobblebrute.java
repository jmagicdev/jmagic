package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;

@Name("Cobblebrute")
@Types({Type.CREATURE})
@SubTypes({SubType.ELEMENTAL})
@ManaCost("3R")
@Printings({@Printings.Printed(ex = Expansion.RETURN_TO_RAVNICA, r = Rarity.COMMON)})
@ColorIdentity({Color.RED})
public final class Cobblebrute extends Card
{
	public Cobblebrute(GameState state)
	{
		super(state);

		this.setPower(5);
		this.setToughness(2);
	}
}
