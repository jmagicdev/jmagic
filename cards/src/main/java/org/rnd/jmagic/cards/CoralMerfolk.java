package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;

@Name("Coral Merfolk")
@Types({Type.CREATURE})
@SubTypes({SubType.MERFOLK})
@ManaCost("1U")
@Printings({@Printings.Printed(ex = Expansion.MAGIC_2012, r = Rarity.COMMON), @Printings.Printed(ex = Expansion.MAGIC_2010, r = Rarity.COMMON), @Printings.Printed(ex = Expansion.SEVENTH_EDITION, r = Rarity.COMMON), @Printings.Printed(ex = Expansion.URZAS_SAGA, r = Rarity.COMMON)})
@ColorIdentity({Color.BLUE})
public final class CoralMerfolk extends Card
{
	public CoralMerfolk(GameState state)
	{
		super(state);

		this.setPower(2);
		this.setToughness(1);
	}
}
