package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.expansions.*;

@Name("Centaur Courser")
@Types({Type.CREATURE})
@SubTypes({SubType.CENTAUR, SubType.WARRIOR})
@ManaCost("2G")
@Printings({@Printings.Printed(ex = Magic2013.class, r = Rarity.COMMON), @Printings.Printed(ex = Magic2010.class, r = Rarity.COMMON)})
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
