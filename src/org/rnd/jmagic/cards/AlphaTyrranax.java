package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;

@Name("Alpha Tyrranax")
@Types({Type.CREATURE})
@SubTypes({SubType.BEAST})
@ManaCost("4GG")
@Printings({@Printings.Printed(ex = Expansion.SCARS_OF_MIRRODIN, r = Rarity.COMMON)})
@ColorIdentity({Color.GREEN})
public final class AlphaTyrranax extends Card
{
	public AlphaTyrranax(GameState state)
	{
		super(state);

		this.setPower(6);
		this.setToughness(5);
	}
}
