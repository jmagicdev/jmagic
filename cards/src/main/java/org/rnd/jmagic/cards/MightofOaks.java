package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Might of Oaks")
@Types({Type.INSTANT})
@ManaCost("3G")
@ColorIdentity({Color.GREEN})
public final class MightofOaks extends Card
{
	public MightofOaks(GameState state)
	{
		super(state);

		Target target = this.addTarget(CreaturePermanents.instance(), "target creature");

		this.addEffect(ptChangeUntilEndOfTurn(targetedBy(target), (+7), (+7), "Target creature gets +7/+7 until end of turn."));
	}
}
