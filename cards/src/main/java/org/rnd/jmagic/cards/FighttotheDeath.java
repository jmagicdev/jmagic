package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Fight to the Death")
@Types({Type.INSTANT})
@ManaCost("RW")
@Printings({@Printings.Printed(ex = Expansion.ALARA_REBORN, r = Rarity.RARE)})
@ColorIdentity({Color.WHITE, Color.RED})
public final class FighttotheDeath extends Card
{
	public FighttotheDeath(GameState state)
	{
		super(state);

		this.addEffect(destroy(Union.instance(Blocking.instance(), Blocked.instance()), "Destroy all blocking creatures and all blocked creatures."));
	}
}
