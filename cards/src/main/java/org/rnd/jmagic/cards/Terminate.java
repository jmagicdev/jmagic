package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Terminate")
@Types({Type.INSTANT})
@ManaCost("BR")
@ColorIdentity({Color.BLACK, Color.RED})
public final class Terminate extends Card
{
	public Terminate(GameState state)
	{
		super(state);

		// Destroy target creature. It can't be regenerated.
		Target target = this.addTarget(CreaturePermanents.instance(), "target creature");
		this.addEffects(bury(this, targetedBy(target), "Destroy target creature. It can't be regenerated."));
	}
}
