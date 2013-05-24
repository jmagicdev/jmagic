package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Martial Glory")
@Types({Type.INSTANT})
@ManaCost("RW")
@Printings({@Printings.Printed(ex = Expansion.GATECRASH, r = Rarity.COMMON)})
@ColorIdentity({Color.WHITE, Color.RED})
public final class MartialGlory extends Card
{
	public MartialGlory(GameState state)
	{
		super(state);

		// Target creature gets +3/+0 until end of turn.
		Target target1 = this.addTarget(CreaturePermanents.instance(), "target creature to get +3/+0");
		this.addEffect(ptChangeUntilEndOfTurn(targetedBy(target1), +3, 0, "Target creature gets +3/+0 until end of turn."));

		// Target creature gets +0/+3 until end of turn.
		Target target2 = this.addTarget(CreaturePermanents.instance(), "target creature to get +0/+3");
		this.addEffect(ptChangeUntilEndOfTurn(targetedBy(target2), 0, +3, "Target creature gets +0/+3 until end of turn."));
	}
}
