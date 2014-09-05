package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.expansions.*;

@Name("Righteous Charge")
@Types({Type.SORCERY})
@ManaCost("1WW")
@Printings({@Printings.Printed(ex = Gatecrash.class, r = Rarity.UNCOMMON), @Printings.Printed(ex = Starter1999.class, r = Rarity.UNCOMMON), @Printings.Printed(ex = PortalSecondAge.class, r = Rarity.COMMON)})
@ColorIdentity({Color.WHITE})
public final class RighteousCharge extends Card
{
	public RighteousCharge(GameState state)
	{
		super(state);

		// Creatures you control get +2/+2 until end of turn.
		this.addEffect(ptChangeUntilEndOfTurn(CREATURES_YOU_CONTROL, +2, +2, "Creatures you control get +2/+2 until end of turn."));
	}
}
