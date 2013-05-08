package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Dispense Justice")
@Types({Type.INSTANT})
@ManaCost("2W")
@Printings({@Printings.Printed(ex = Expansion.SCARS_OF_MIRRODIN, r = Rarity.UNCOMMON)})
@ColorIdentity({Color.WHITE})
public final class DispenseJustice extends Card
{
	public DispenseJustice(GameState state)
	{
		super(state);

		// Target player sacrifices an attacking creature.
		// Metalcraft \u2014 That player sacrifices two attacking creatures
		// instead if you control three or more artifacts.
		SetGenerator target = targetedBy(this.addTarget(Players.instance(), "target player"));
		SetGenerator number = IfThenElse.instance(Metalcraft.instance(), numberGenerator(2), numberGenerator(1));
		this.addEffect(sacrifice(target, number, Attacking.instance(), "Target player sacrifices an attacking creature.\n\nThat player sacrifices two attacking creatures instead if you control three or more artifacts."));
	}
}
