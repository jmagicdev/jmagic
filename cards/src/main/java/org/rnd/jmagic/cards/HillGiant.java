package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.expansions.*;

@Name("Hill Giant")
@Types({Type.CREATURE})
@SubTypes({SubType.GIANT})
@ManaCost("3R")
@Printings({@Printings.Printed(ex = TenthEdition.class, r = Rarity.COMMON), @Printings.Printed(ex = NinthEdition.class, r = Rarity.COMMON), @Printings.Printed(ex = EighthEdition.class, r = Rarity.COMMON), @Printings.Printed(ex = SeventhEdition.class, r = Rarity.COMMON), @Printings.Printed(ex = Portal.class, r = Rarity.COMMON), @Printings.Printed(ex = FifthEdition.class, r = Rarity.COMMON), @Printings.Printed(ex = FourthEdition.class, r = Rarity.COMMON), @Printings.Printed(ex = RevisedEdition.class, r = Rarity.COMMON), @Printings.Printed(ex = UnlimitedEdition.class, r = Rarity.COMMON), @Printings.Printed(ex = LimitedEditionBeta.class, r = Rarity.COMMON), @Printings.Printed(ex = LimitedEditionAlpha.class, r = Rarity.COMMON)})
@ColorIdentity({Color.RED})
public final class HillGiant extends Card
{
	public HillGiant(GameState state)
	{
		super(state);

		this.setPower(3);
		this.setToughness(3);
	}
}
