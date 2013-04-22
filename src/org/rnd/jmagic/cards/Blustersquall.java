package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Blustersquall")
@Types({Type.INSTANT})
@ManaCost("U")
@Printings({@Printings.Printed(ex = Expansion.RETURN_TO_RAVNICA, r = Rarity.UNCOMMON)})
@ColorIdentity({Color.BLUE})
public final class Blustersquall extends Card
{
	public Blustersquall(GameState state)
	{
		super(state);

		// Tap target creature you don't control.
		SetGenerator target = targetedBy(this.addTarget(RelativeComplement.instance(CreaturePermanents.instance(), ControlledBy.instance(You.instance())), "target creature you don't control"));
		this.addEffect(tap(target, "Tap target creature you don't control."));

		// Overload (3)(U) (You may cast this spell for its overload cost. If
		// you do, change its text by replacing all instances of "target" with
		// "each.")
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Overload(state, "(3)(U)"));
	}
}
