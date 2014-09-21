package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Become Immense")
@Types({Type.INSTANT})
@ManaCost("5G")
@ColorIdentity({Color.GREEN})
public final class BecomeImmense extends Card
{
	public BecomeImmense(GameState state)
	{
		super(state);

		// Delve (Each card you exile from your graveyard while casting this
		// spell pays for (1).)
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Delve(state));

		// Target creature gets +6/+6 until end of turn.
		SetGenerator target = targetedBy(this.addTarget(CreaturePermanents.instance(), "target creature"));
		this.addEffect(ptChangeUntilEndOfTurn(target, +6, +6, "Target creature gets +6/+6 until end of turn."));
	}
}
