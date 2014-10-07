package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Kiora's Dismissal")
@Types({Type.INSTANT})
@ManaCost("U")
@ColorIdentity({Color.BLUE})
public final class KiorasDismissal extends Card
{
	public KiorasDismissal(GameState state)
	{
		super(state);

		// Strive \u2014 Kiora's Dismissal costs (U) more to cast for each
		// target beyond the first.
		this.addAbility(new org.rnd.jmagic.abilities.Strive(state, this.getName(), "(U)"));

		// Return any number of target enchantments to their owners' hands.
		SetGenerator target = targetedBy(this.addTarget(EnchantmentPermanents.instance(), "any number of target enchantments").setNumber(0, null));
		this.addEffect(bounce(target, "Return any number of target enchantments to their owners' hands."));
	}
}
