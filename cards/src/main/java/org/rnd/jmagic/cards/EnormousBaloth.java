package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.expansions.*;

@Name("Enormous Baloth")
@Types({Type.CREATURE})
@SubTypes({SubType.BEAST})
@ManaCost("6G")
@Printings({@Printings.Printed(ex = Magic2010.class, r = Rarity.UNCOMMON), @Printings.Printed(ex = TenthEdition.class, r = Rarity.UNCOMMON), @Printings.Printed(ex = NinthEdition.class, r = Rarity.UNCOMMON), @Printings.Printed(ex = EighthEdition.class, r = Rarity.UNCOMMON), @Printings.Printed(ex = Legions.class, r = Rarity.UNCOMMON)})
@ColorIdentity({Color.GREEN})
public final class EnormousBaloth extends Card
{
	public EnormousBaloth(GameState state)
	{
		super(state);

		this.setPower(7);
		this.setToughness(7);
	}
}
