package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;

@Name("Mass of Ghouls")
@Types({Type.CREATURE})
@SubTypes({SubType.WARRIOR, SubType.ZOMBIE})
@ManaCost("3BB")
@Printings({@Printings.Printed(ex = Expansion.TENTH_EDITION, r = Rarity.COMMON), @Printings.Printed(ex = Expansion.FUTURE_SIGHT, r = Rarity.COMMON)})
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
