package org.rnd.jmagic.cards;

import org.rnd.jmagic.abilities.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.expansions.*;

@Name("Essence Warden")
@Types({Type.CREATURE})
@SubTypes({SubType.ELF, SubType.SHAMAN})
@ManaCost("G")
@Printings({@Printings.Printed(ex = PlanarChaos.class, r = Rarity.COMMON)})
@ColorIdentity({Color.GREEN})
public final class EssenceWarden extends Card
{
	public EssenceWarden(GameState state)
	{
		super(state);

		this.setPower(1);
		this.setToughness(1);

		this.addAbility(new WardenTrigger(state));
	}
}
