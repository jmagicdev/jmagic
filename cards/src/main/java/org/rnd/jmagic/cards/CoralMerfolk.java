package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.expansions.*;

@Name("Coral Merfolk")
@Types({Type.CREATURE})
@SubTypes({SubType.MERFOLK})
@ManaCost("1U")
@Printings({@Printings.Printed(ex = Magic2012.class, r = Rarity.COMMON), @Printings.Printed(ex = Magic2010.class, r = Rarity.COMMON), @Printings.Printed(ex = SeventhEdition.class, r = Rarity.COMMON), @Printings.Printed(ex = UrzasSaga.class, r = Rarity.COMMON)})
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
