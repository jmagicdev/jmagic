package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.expansions.*;

@Name("Death Wind")
@Types({Type.INSTANT})
@ManaCost("XB")
@Printings({@Printings.Printed(ex = AvacynRestored.class, r = Rarity.COMMON)})
@ColorIdentity({Color.BLACK})
public final class DeathWind extends Card
{
	public DeathWind(GameState state)
	{
		super(state);

		// Target creature gets -X/-X until end of turn.
		SetGenerator target = targetedBy(this.addTarget(CreaturePermanents.instance(), "target creature"));
		SetGenerator minusX = Subtract.instance(numberGenerator(0), ValueOfX.instance(This.instance()));
		this.addEffect(ptChangeUntilEndOfTurn(target, minusX, minusX, "Target creature gets -X/-X until end of turn."));
	}
}
