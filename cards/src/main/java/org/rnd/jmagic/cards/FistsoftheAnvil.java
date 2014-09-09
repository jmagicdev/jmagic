package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Fists of the Anvil")
@Types({Type.INSTANT})
@ManaCost("1R")
@ColorIdentity({Color.RED})
public final class FistsoftheAnvil extends Card
{
	public FistsoftheAnvil(GameState state)
	{
		super(state);

		Target target = this.addTarget(CreaturePermanents.instance(), "target creature");

		this.addEffect(ptChangeUntilEndOfTurn(targetedBy(target), +4, +0, "Target creature gets +4/+0 until end of turn."));
	}
}
