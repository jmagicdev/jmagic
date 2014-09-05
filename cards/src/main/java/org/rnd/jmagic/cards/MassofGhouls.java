package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.expansions.*;

@Name("Mass of Ghouls")
@Types({Type.CREATURE})
@SubTypes({SubType.WARRIOR, SubType.ZOMBIE})
@ManaCost("3BB")
@Printings({@Printings.Printed(ex = TenthEdition.class, r = Rarity.COMMON), @Printings.Printed(ex = FutureSight.class, r = Rarity.COMMON)})
@ColorIdentity({Color.BLACK})
public final class MassofGhouls extends Card
{
	public MassofGhouls(GameState state)
	{
		super(state);

		this.setPower(5);
		this.setToughness(3);
	}
}
