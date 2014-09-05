package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.expansions.*;

@Name("Elvish Mystic")
@Types({Type.CREATURE})
@SubTypes({SubType.ELF, SubType.DRUID})
@ManaCost("G")
@Printings({@Printings.Printed(ex = Magic2015CoreSet.class, r = Rarity.COMMON), @Printings.Printed(ex = Magic2014CoreSet.class, r = Rarity.COMMON)})
@ColorIdentity({Color.GREEN})
public final class ElvishMystic extends Card
{
	public ElvishMystic(GameState state)
	{
		super(state);

		this.setPower(1);
		this.setToughness(1);

		// (T): Add (G) to your mana pool.
		this.addAbility(new org.rnd.jmagic.abilities.TapForG(state));
	}
}
