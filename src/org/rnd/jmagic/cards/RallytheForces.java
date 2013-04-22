package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Rally the Forces")
@Types({Type.INSTANT})
@ManaCost("2R")
@Printings({@Printings.Printed(ex = Expansion.MIRRODIN_BESIEGED, r = Rarity.COMMON)})
@ColorIdentity({Color.RED})
public final class RallytheForces extends Card
{
	public RallytheForces(GameState state)
	{
		super(state);

		// Attacking creatures get +1/+0 and gain first strike until end of
		// turn.
		this.addEffect(ptChangeAndAbilityUntilEndOfTurn(Attacking.instance(), +1, +0, "Attacking creatures get +1/+0 and gain first strike until end of turn.", org.rnd.jmagic.abilities.keywords.FirstStrike.class));
	}
}
