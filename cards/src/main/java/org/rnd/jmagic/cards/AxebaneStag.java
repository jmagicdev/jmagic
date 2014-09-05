package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.expansions.*;

@Name("Axebane Stag")
@Types({Type.CREATURE})
@SubTypes({SubType.ELK})
@ManaCost("6G")
@Printings({@Printings.Printed(ex = ReturnToRavnica.class, r = Rarity.COMMON)})
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
