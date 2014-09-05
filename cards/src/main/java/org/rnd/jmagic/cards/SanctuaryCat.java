package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.expansions.*;

@Name("Sanctuary Cat")
@Types({Type.CREATURE})
@SubTypes({SubType.CAT})
@ManaCost("W")
@Printings({@Printings.Printed(ex = DarkAscension.class, r = Rarity.COMMON)})
@ColorIdentity({Color.WHITE})
public final class SanctuaryCat extends Card
{
	public SanctuaryCat(GameState state)
	{
		super(state);

		this.setPower(1);
		this.setToughness(2);
	}
}
