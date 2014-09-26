package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Brimstone Volley")
@Types({Type.INSTANT})
@ManaCost("2R")
@ColorIdentity({Color.RED})
public final class BrimstoneVolley extends Card
{
	public BrimstoneVolley(GameState state)
	{
		super(state);

		// Brimstone Volley deals 3 damage to target creature or player.

		// Morbid \u2014 Brimstone Volley deals 5 damage to that creature or
		// player instead if a creature died this turn.
		SetGenerator amount = IfThenElse.instance(Morbid.instance(), numberGenerator(5), numberGenerator(3));
		SetGenerator target = targetedBy(this.addTarget(CREATURES_AND_PLAYERS, "target creature or player"));
		this.addEffect(spellDealDamage(amount, target, "Brimstone Volley deals 3 damage to target creature or player.\n\nMorbid \u2014 Brimstone Volley deals 5 damage to that creature or player instead if a creature died this turn."));

		state.ensureTracker(new Morbid.Tracker());
	}
}
