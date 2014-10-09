package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Rouse the Mob")
@Types({Type.INSTANT})
@ManaCost("R")
@ColorIdentity({Color.RED})
public final class RousetheMob extends Card
{
	public RousetheMob(GameState state)
	{
		super(state);

		// Strive \u2014 Rouse the Mob costs (2)(R) more to cast for each target
		// beyond the first.
		this.addAbility(new org.rnd.jmagic.abilities.Strive(state, this.getName(), "(2)(R)"));

		// Any number of target creatures each get +2/+0 and gain trample until
		// end of turn.
		SetGenerator target = targetedBy(this.addTarget(CreaturePermanents.instance(), "any number of target creatures").setNumber(0, null));
		this.addEffect(ptChangeAndAbilityUntilEndOfTurn(target, +2, +0, "Any number of target creatures each  +2/+0 and gain trample until end of turn.", org.rnd.jmagic.abilities.keywords.Trample.class));
	}
}
