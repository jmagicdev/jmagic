package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Frenzied Tilling")
@Types({Type.SORCERY})
@ManaCost("3RG")
@Printings({@Printings.Printed(ex = Expansion.GATECRASH, r = Rarity.UNCOMMON), @Printings.Printed(ex = Expansion.INVASION, r = Rarity.COMMON)})
@ColorIdentity({Color.GREEN, Color.RED})
public final class FrenziedTilling extends Card
{
	public FrenziedTilling(GameState state)
	{
		super(state);

		// Destroy target land. Search your library for a basic land card and
		// put that card onto the battlefield tapped. Then shuffle your library.
		SetGenerator target = targetedBy(this.addTarget(LandPermanents.instance(), "target land"));

		this.addEffect(destroy(target, "Destroy target land."));

		this.addEffect(searchYourLibraryForABasicLandCardAndPutItOntoTheBattlefield(true));
	}
}
