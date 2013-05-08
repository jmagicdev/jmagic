package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;

@Name("Axebane Stag")
@Types({Type.CREATURE})
@SubTypes({SubType.ELK})
@ManaCost("6G")
@Printings({@Printings.Printed(ex = Expansion.RETURN_TO_RAVNICA, r = Rarity.COMMON)})
@ColorIdentity({Color.GREEN})
public final class AxebaneStag extends Card
{
	public AxebaneStag(GameState state)
	{
		super(state);

		this.setPower(6);
		this.setToughness(7);
	}
}
