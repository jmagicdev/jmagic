package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Aerial Formation")
@Types({Type.INSTANT})
@ManaCost("U")
@ColorIdentity({Color.BLUE})
public final class AerialFormation extends Card
{
	public AerialFormation(GameState state)
	{
		super(state);

		// Strive \u2014 Aerial Formation costs (2)(U) more to cast for each
		// target beyond the first.
		this.addAbility(new org.rnd.jmagic.abilities.Strive(state, this.getName(), "(2)(U)"));

		// Any number of target creatures each get +1/+1 and gain flying until
		// end of turn.
		SetGenerator target = targetedBy(this.addTarget(CreaturePermanents.instance(), "any number of target creatures").setNumber(0, null));
		this.addEffect(ptChangeAndAbilityUntilEndOfTurn(target, +1, +1, "Any number of target creatures get +1/+1 and gain flying until end of turn.", org.rnd.jmagic.abilities.keywords.Flying.class));
	}
}
