package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;

@Name("Glorious Charge")
@Types({Type.INSTANT})
@ManaCost("1W")
@ColorIdentity({Color.WHITE})
public final class GloriousCharge extends Card
{
	public GloriousCharge(GameState state)
	{
		super(state);

		// Creatures you control get +1/+1 until end of turn.
		this.addEffect(ptChangeUntilEndOfTurn(CREATURES_YOU_CONTROL, +1, +1, "Creatures you control get +1/+1 until end of turn."));
	}
}
