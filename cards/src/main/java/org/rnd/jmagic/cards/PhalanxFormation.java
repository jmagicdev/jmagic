package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Phalanx Formation")
@Types({Type.INSTANT})
@ManaCost("2W")
@ColorIdentity({Color.WHITE})
public final class PhalanxFormation extends Card
{
	public PhalanxFormation(GameState state)
	{
		super(state);

		// Strive \u2014 Phalanx Formation costs (1)(W) more to cast for each
		// target beyond the first.
		this.addAbility(new org.rnd.jmagic.abilities.Strive(state, this.getName(), "(1)(W)"));

		// Any number of target creatures each gain double strike until end of
		// turn.
		SetGenerator target = targetedBy(this.addTarget(CreaturePermanents.instance(), "any number of target creatures").setNumber(0, null));
		this.addEffect(addAbilityUntilEndOfTurn(target, org.rnd.jmagic.abilities.keywords.DoubleStrike.class, "Any number of target creature each gain double strike until end of turn."));
	}
}
