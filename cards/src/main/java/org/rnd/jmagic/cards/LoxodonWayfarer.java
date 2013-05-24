package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;

@Name("Loxodon Wayfarer")
@Types({Type.CREATURE})
@SubTypes({SubType.ELEPHANT, SubType.MONK})
@ManaCost("2W")
@Printings({@Printings.Printed(ex = Expansion.SCARS_OF_MIRRODIN, r = Rarity.COMMON)})
@ColorIdentity({Color.WHITE})
public final class LoxodonWayfarer extends Card
{
	public LoxodonWayfarer(GameState state)
	{
		super(state);

		this.setPower(1);
		this.setToughness(5);
	}
}
