package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.expansions.*;

@Name("Earth Elemental")
@Types({Type.CREATURE})
@SubTypes({SubType.ELEMENTAL})
@ManaCost("3RR")
@Printings({@Printings.Printed(ex = TenthEdition.class, r = Rarity.UNCOMMON), @Printings.Printed(ex = Starter1999.class, r = Rarity.UNCOMMON), @Printings.Printed(ex = FourthEdition.class, r = Rarity.UNCOMMON), @Printings.Printed(ex = RevisedEdition.class, r = Rarity.UNCOMMON), @Printings.Printed(ex = UnlimitedEdition.class, r = Rarity.UNCOMMON), @Printings.Printed(ex = LimitedEditionBeta.class, r = Rarity.UNCOMMON), @Printings.Printed(ex = LimitedEditionAlpha.class, r = Rarity.UNCOMMON)})
@ColorIdentity({Color.RED})
public final class EarthElemental extends Card
{
	public EarthElemental(GameState state)
	{
		super(state);

		this.setPower(4);
		this.setToughness(5);
	}
}
