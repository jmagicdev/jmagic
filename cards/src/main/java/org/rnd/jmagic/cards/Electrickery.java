package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Electrickery")
@Types({Type.INSTANT})
@ManaCost("R")
@Printings({@Printings.Printed(ex = Expansion.RETURN_TO_RAVNICA, r = Rarity.COMMON)})
@ColorIdentity({Color.RED})
public final class Electrickery extends Card
{
	public Electrickery(GameState state)
	{
		super(state);

		// Electrickery deals 1 damage to target creature you don't control.
		SetGenerator target = targetedBy(this.addTarget(RelativeComplement.instance(CreaturePermanents.instance(), ControlledBy.instance(You.instance())), "target creature you don't control"));
		this.addEffect(spellDealDamage(1, target, "Electrickery deals 1 damage to target creature you don't control."));

		// Overload (1)(R)
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Overload(state, "(1)(R)"));
	}
}
