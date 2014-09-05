package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Titan's Strength")
@Types({Type.INSTANT})
@ManaCost("R")
@Printings({@Printings.Printed(ex = Expansion.THEROS, r = Rarity.COMMON)})
@ColorIdentity({Color.RED})
public final class TitansStrength extends Card
{
	public TitansStrength(GameState state)
	{
		super(state);


		// Target creature gets +3/+1 until end of turn.
		Target target = this.addTarget(CreaturePermanents.instance(), "target creature");
		this.addEffect(ptChangeUntilEndOfTurn(targetedBy(target), (+3), (+1), "Target creature gets +3/+1 until end of turn."));

		// Scry 1.
		this.addEffect(scry(1, "Scry 1."));
	}
}
