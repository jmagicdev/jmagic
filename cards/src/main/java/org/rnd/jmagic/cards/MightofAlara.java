package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Might of Alara")
@Types({Type.INSTANT})
@ManaCost("G")
@ColorIdentity({Color.GREEN})
public final class MightofAlara extends Card
{
	public MightofAlara(GameState state)
	{
		super(state);

		Target target = this.addTarget(CreaturePermanents.instance(), "target creature");

		SetGenerator boost = Domain.instance(You.instance());
		this.addEffect(ptChangeUntilEndOfTurn(targetedBy(target), boost, boost, "Domain \u2014 Target creature gets +1/+1 until end of turn for each basic land type among lands you control."));
	}
}
