package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;

@Name("Elite Vanguard")
@Types({Type.CREATURE})
@SubTypes({SubType.SOLDIER, SubType.HUMAN})
@ManaCost("W")
@Printings({@Printings.Printed(ex = Expansion.MAGIC_2012, r = Rarity.UNCOMMON), @Printings.Printed(ex = Expansion.MAGIC_2011, r = Rarity.UNCOMMON), @Printings.Printed(ex = Expansion.MAGIC_2010, r = Rarity.UNCOMMON)})
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
