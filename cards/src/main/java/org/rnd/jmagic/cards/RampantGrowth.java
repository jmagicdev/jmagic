package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;

@Name("Rampant Growth")
@Types({Type.SORCERY})
@ManaCost("1G")
@ColorIdentity({Color.GREEN})
public final class RampantGrowth extends Card
{
	public RampantGrowth(GameState state)
	{
		super(state);

		this.addEffect(searchYourLibraryForABasicLandCardAndPutItOntoTheBattlefield(true));
	}
}
