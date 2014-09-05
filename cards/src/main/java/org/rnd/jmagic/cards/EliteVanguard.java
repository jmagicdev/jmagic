package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.expansions.*;

@Name("Elite Vanguard")
@Types({Type.CREATURE})
@SubTypes({SubType.SOLDIER, SubType.HUMAN})
@ManaCost("W")
@Printings({@Printings.Printed(ex = Magic2012.class, r = Rarity.UNCOMMON), @Printings.Printed(ex = Magic2011.class, r = Rarity.UNCOMMON), @Printings.Printed(ex = Magic2010.class, r = Rarity.UNCOMMON)})
@ColorIdentity({Color.WHITE})
public final class EliteVanguard extends Card
{
	public EliteVanguard(GameState state)
	{
		super(state);

		this.setPower(2);
		this.setToughness(1);
	}
}
