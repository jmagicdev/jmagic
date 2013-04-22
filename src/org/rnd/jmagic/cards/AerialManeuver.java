package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Aerial Maneuver")
@Types({Type.INSTANT})
@ManaCost("1W")
@Printings({@Printings.Printed(ex = Expansion.GATECRASH, r = Rarity.COMMON)})
@ColorIdentity({Color.WHITE})
public final class AerialManeuver extends Card
{
	public AerialManeuver(GameState state)
	{
		super(state);

		// Target creature gets +1/+1 and gains flying and first strike until
		// end of turn.
		SetGenerator target = targetedBy(this.addTarget(CreaturePermanents.instance(), "target creature"));
		this.addEffect(ptChangeAndAbilityUntilEndOfTurn(target, +1, +1, "Target creature gets +1/+1 and gains flying and first strike until end of turn", org.rnd.jmagic.abilities.keywords.Flying.class, org.rnd.jmagic.abilities.keywords.FirstStrike.class));
	}
}
