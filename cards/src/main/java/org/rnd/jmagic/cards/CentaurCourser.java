package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;

@Name("Centaur Courser")
@Types({Type.CREATURE})
@SubTypes({SubType.CENTAUR, SubType.WARRIOR})
@ManaCost("2G")
@Printings({@Printings.Printed(ex = Expansion.MAGIC_2013, r = Rarity.COMMON), @Printings.Printed(ex = Expansion.MAGIC_2010, r = Rarity.COMMON)})
@ColorIdentity({Color.GREEN})
public final class CentaurCourser extends Card
{
	public CentaurCourser(GameState state)
	{
		super(state);

		this.setPower(3);
		this.setToughness(3);
	}
}
