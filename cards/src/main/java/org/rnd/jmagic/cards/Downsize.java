package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Downsize")
@Types({Type.INSTANT})
@ManaCost("U")
@ColorIdentity({Color.BLUE})
public final class Downsize extends Card
{
	public Downsize(GameState state)
	{
		super(state);

		// Target creature you don't control gets -4/-0 until end of turn.
		SetGenerator target = targetedBy(this.addTarget(RelativeComplement.instance(CreaturePermanents.instance(), CREATURES_YOU_CONTROL), "target creature you don't control"));

		this.addEffect(ptChangeUntilEndOfTurn(target, -4, -0, "Target creature you don't control gets -4/-0 until end of turn."));

		// Overload (2)(U) (You may cast this spell for its overload cost. If
		// you do, change its text by replacing all instances of "target" with
		// "each.")
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Overload(state, "(2)(U)"));
	}
}
