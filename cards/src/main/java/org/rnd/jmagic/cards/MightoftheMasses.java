package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Might of the Masses")
@Types({Type.INSTANT})
@ManaCost("G")
@ColorIdentity({Color.GREEN})
public final class MightoftheMasses extends Card
{
	public MightoftheMasses(GameState state)
	{
		super(state);

		// Target creature gets +1/+1 until end of turn for each creature you
		// control.
		SetGenerator target = targetedBy(this.addTarget(CreaturePermanents.instance(), "target creature"));
		SetGenerator amount = Count.instance(CREATURES_YOU_CONTROL);
		this.addEffect(ptChangeUntilEndOfTurn(target, amount, amount, "Target creature gets +1/+1 until end of turn for each creature you control."));
	}
}
