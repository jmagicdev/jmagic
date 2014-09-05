package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.expansions.*;

@Name("Rampant Growth")
@Types({Type.SORCERY})
@ManaCost("1G")
@Printings({@Printings.Printed(ex = Magic2012.class, r = Rarity.COMMON), @Printings.Printed(ex = Magic2010.class, r = Rarity.COMMON), @Printings.Printed(ex = TenthEdition.class, r = Rarity.COMMON), @Printings.Printed(ex = NinthEdition.class, r = Rarity.COMMON), @Printings.Printed(ex = EighthEdition.class, r = Rarity.COMMON), @Printings.Printed(ex = SeventhEdition.class, r = Rarity.COMMON), @Printings.Printed(ex = ClassicSixthEdition.class, r = Rarity.COMMON), @Printings.Printed(ex = Tempest.class, r = Rarity.COMMON), @Printings.Printed(ex = Mirage.class, r = Rarity.COMMON)})
@ColorIdentity({Color.GREEN})
public final class RampantGrowth extends Card
{
	public RampantGrowth(GameState state)
	{
		super(state);

		this.addEffect(searchYourLibraryForABasicLandCardAndPutItOntoTheBattlefield(true));
	}
}
