package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;

@Name("Dynacharge")
@Types({Type.INSTANT})
@ManaCost("R")
@Printings({@Printings.Printed(ex = Expansion.RETURN_TO_RAVNICA, r = Rarity.COMMON)})
@ColorIdentity({Color.RED})
public final class Dynacharge extends Card
{
	public Dynacharge(GameState state)
	{
		super(state);

		// Target creature you control gets +2/+0 until end of turn.
		SetGenerator t = targetedBy(this.addTarget(CREATURES_YOU_CONTROL, "target creature you control"));
		this.addEffect(ptChangeUntilEndOfTurn(t, +2, +0, "Target creature you control gets +2/+0 until end of turn."));

		// Overload (2)(R) (You may cast this spell for its overload cost. If
		// you do, change its text by replacing all instances of "target" with
		// "each.")
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Overload(state, "(2)(R)"));
	}
}
