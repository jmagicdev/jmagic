package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.expansions.*;

@Name("Mizzium Mortars")
@Types({Type.SORCERY})
@ManaCost("1R")
@Printings({@Printings.Printed(ex = ReturnToRavnica.class, r = Rarity.RARE)})
@ColorIdentity({Color.RED})
public final class MizziumMortars extends Card
{
	public MizziumMortars(GameState state)
	{
		super(state);

		// Mizzium Mortars deals 4 damage to target creature you don't control.
		SetGenerator target = targetedBy(this.addTarget(RelativeComplement.instance(CreaturePermanents.instance(), ControlledBy.instance(You.instance())), "target creature you don't control"));
		this.addEffect(spellDealDamage(4, target, "Mizzium Mortars deals 4 damage to target creature you don't control."));

		// Overload (3)(R)(R)(R) (You may cast this spell for its overload cost.
		// If you do, change its text by replacing all instances of "target"
		// with "each.")
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Overload(state, "(3)(R)(R)(R)"));
	}
}
