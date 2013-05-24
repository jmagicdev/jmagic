package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;

@Name("Enormous Baloth")
@Types({Type.CREATURE})
@SubTypes({SubType.BEAST})
@ManaCost("6G")
@Printings({@Printings.Printed(ex = Expansion.MAGIC_2010, r = Rarity.UNCOMMON), @Printings.Printed(ex = Expansion.TENTH_EDITION, r = Rarity.UNCOMMON), @Printings.Printed(ex = Expansion.NINTH_EDITION, r = Rarity.UNCOMMON), @Printings.Printed(ex = Expansion.EIGHTH_EDITION, r = Rarity.UNCOMMON), @Printings.Printed(ex = Expansion.LEGIONS, r = Rarity.UNCOMMON)})
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
