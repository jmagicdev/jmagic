package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.expansions.*;

@Name("Dissolve")
@Types({Type.INSTANT})
@ManaCost("1UU")
@Printings({@Printings.Printed(ex = Theros.class, r = Rarity.UNCOMMON)})
@ColorIdentity({Color.BLUE})
public final class Dissolve extends Card
{
	public Dissolve(GameState state)
	{
		super(state);

		// Counter target spell. Scry 1. (Look at the top card of your library.
		// You may put that card on the bottom of your library.)
		Target target = this.addTarget(Spells.instance(), "target spells");
		this.addEffect(counter(targetedBy(target), "Counter target spell."));
		this.addEffect(scry(1, "Scry 1."));
	}
}
