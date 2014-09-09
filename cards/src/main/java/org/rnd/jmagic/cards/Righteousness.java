package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Righteousness")
@Types({Type.INSTANT})
@ManaCost("W")
@ColorIdentity({Color.WHITE})
public final class Righteousness extends Card
{
	public Righteousness(GameState state)
	{
		super(state);

		Target target = this.addTarget(Intersect.instance(Blocking.instance(), CreaturePermanents.instance()), "target blocking creature");

		this.addEffect(ptChangeUntilEndOfTurn(targetedBy(target), (+7), (+7), "Target blocking creature gets +7/+7 until end of turn."));
	}
}
