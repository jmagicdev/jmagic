package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.expansions.*;

@Name("Karplusan Forest")
@Types({Type.LAND})
@Printings({@Printings.Printed(ex = TenthEdition.class, r = Rarity.RARE), @Printings.Printed(ex = NinthEdition.class, r = Rarity.RARE), @Printings.Printed(ex = SeventhEdition.class, r = Rarity.RARE), @Printings.Printed(ex = ClassicSixthEdition.class, r = Rarity.RARE), @Printings.Printed(ex = FifthEdition.class, r = Rarity.RARE), @Printings.Printed(ex = IceAge.class, r = Rarity.RARE)})
@ColorIdentity({Color.GREEN, Color.RED})
public final class KarplusanForest extends org.rnd.jmagic.cardTemplates.PainLand
{
	public KarplusanForest(GameState state)
	{
		super(state, "RG");
	}
}
