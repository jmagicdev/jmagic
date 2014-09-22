package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Crater's Claws")
@Types({Type.SORCERY})
@ManaCost("XR")
@ColorIdentity({Color.RED})
public final class CratersClaws extends Card
{
	public CratersClaws(GameState state)
	{
		super(state);

		// Crater's Claws deals X damage to target creature or player.

		// Ferocious \u2014 Crater's Claws deals X plus 2 damage to that
		// creature or player instead if you control a creature with power 4 or
		// greater.

		SetGenerator target = targetedBy(this.addTarget(CREATURES_AND_PLAYERS, "target creature or player"));

		SetGenerator xPlus2 = Sum.instance(Union.instance(numberGenerator(2), ValueOfX.instance(This.instance())));
		SetGenerator amount = IfThenElse.instance(Ferocious.instance(), xPlus2, ValueOfX.instance(This.instance()));
		this.addEffect(spellDealDamage(amount, target, "Crater's Claws deals X damage to target creature or player.\n\nCrater's Claws deals X plus 2 damage to that creature or player instead if you control a creature with power 4 or greater."));
	}
}
