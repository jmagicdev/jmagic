package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;

@Name("Inspired Charge")
@Types({Type.INSTANT})
@ManaCost("2WW")
@ColorIdentity({Color.WHITE})
public final class InspiredCharge extends Card
{
	public InspiredCharge(GameState state)
	{
		super(state);

		this.addEffect(ptChangeUntilEndOfTurn(CREATURES_YOU_CONTROL, +2, +1, "Creatures you control get +2/+1 until end of turn."));
	}
}
