package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Cruel Feeding")
@Types({Type.INSTANT})
@ManaCost("B")
@ColorIdentity({Color.BLACK})
public final class CruelFeeding extends Card
{
	public CruelFeeding(GameState state)
	{
		super(state);

		// Strive \u2014 Cruel Feeding costs (2)(B) more to cast for each target
		// beyond the first.
		this.addAbility(new org.rnd.jmagic.abilities.Strive(state, this.getName(), "(2)(B)"));

		// Any number of target creatures each get +1/+0 and gain lifelink until
		// end of turn.
		SetGenerator target = targetedBy(this.addTarget(CreaturePermanents.instance(), "any number of target creatures").setNumber(0, null));
		this.addEffect(ptChangeAndAbilityUntilEndOfTurn(target, +1, +0, "Any number of target creatures each get +1/+0 and gain lifelink until end of turn.", org.rnd.jmagic.abilities.keywords.Lifelink.class));
	}
}
