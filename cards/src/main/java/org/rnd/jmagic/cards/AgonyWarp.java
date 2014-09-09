package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Agony Warp")
@Types({Type.INSTANT})
@ManaCost("UB")
@ColorIdentity({Color.BLUE, Color.BLACK})
public final class AgonyWarp extends Card
{
	public AgonyWarp(GameState state)
	{
		super(state);

		Target target1 = this.addTarget(CreaturePermanents.instance(), "target creature to get -3/-0");
		Target target2 = this.addTarget(CreaturePermanents.instance(), "target creature to get -0/-3");

		this.addEffect(ptChangeUntilEndOfTurn(targetedBy(target1), -3, 0, "Target creature gets -3/-0 until end of turn."));
		this.addEffect(ptChangeUntilEndOfTurn(targetedBy(target2), 0, -3, "Target creature gets -0/-3 until end of turn."));
	}
}
