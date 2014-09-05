package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.expansions.*;

@Name("Counterflux")
@Types({Type.INSTANT})
@ManaCost("UUR")
@Printings({@Printings.Printed(ex = ReturnToRavnica.class, r = Rarity.RARE)})
@ColorIdentity({Color.BLUE, Color.RED})
public final class Counterflux extends Card
{
	public Counterflux(GameState state)
	{
		super(state);

		// Counterflux can't be countered by spells or abilities.
		this.addAbility(new org.rnd.jmagic.abilities.CantBeCountered(state, this.getName(), true));

		// Counter target spell you don't control.
		SetGenerator target = targetedBy(this.addTarget(RelativeComplement.instance(Spells.instance(), ControlledBy.instance(You.instance(), Stack.instance())), "target spell you don't control"));
		this.addEffect(counter(target, "Counter target spell you don't control."));

		// Overload (1)(U)(U)(R) (You may cast this spell for its overload cost.
		// If you do, change its text by replacing all instances of "target"
		// with "each.")
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Overload(state, "(1)(U)(U)(R)"));
	}
}
