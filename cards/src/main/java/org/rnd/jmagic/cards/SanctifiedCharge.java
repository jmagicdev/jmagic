package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Sanctified Charge")
@Types({Type.INSTANT})
@ManaCost("4W")
@ColorIdentity({Color.WHITE})
public final class SanctifiedCharge extends Card
{
	public SanctifiedCharge(GameState state)
	{
		super(state);

		// Creatures you control get +2/+1 until end of turn.
		this.addEffect(ptChangeUntilEndOfTurn(CREATURES_YOU_CONTROL, +2, +1, "Creatures you control get +2/+1 until end of turn."));

		// White creatures you control also gain first strike until end of turn.
		// (They deal combat damage before creatures without first strike.)
		SetGenerator racist = Intersect.instance(CREATURES_YOU_CONTROL, HasColor.instance(Color.WHITE));
		this.addEffect(addAbilityUntilEndOfTurn(racist, org.rnd.jmagic.abilities.keywords.FirstStrike.class, "White creatures you control also gain first strike until end of turn."));
	}
}
