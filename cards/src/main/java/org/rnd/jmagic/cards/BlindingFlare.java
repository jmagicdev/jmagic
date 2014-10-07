package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Blinding Flare")
@Types({Type.SORCERY})
@ManaCost("R")
@ColorIdentity({Color.RED})
public final class BlindingFlare extends Card
{
	public BlindingFlare(GameState state)
	{
		super(state);

		// Strive \u2014 Blinding Flare costs (R) more to cast for each target
		// beyond the first.
		this.addAbility(new org.rnd.jmagic.abilities.Strive(state, this.getName(), "(R)"));

		// Any number of target creatures can't block this turn.
		SetGenerator target = targetedBy(this.addTarget(CreaturePermanents.instance(), "any number of target creatures").setNumber(0, null));
		this.addEffect(cantBlockThisTurn(target, "Any number of target creatures can't block this turn."));
	}
}
