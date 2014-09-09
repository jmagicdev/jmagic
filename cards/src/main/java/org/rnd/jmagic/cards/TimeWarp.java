package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Time Warp")
@Types({Type.SORCERY})
@ManaCost("3UU")
@ColorIdentity({Color.BLUE})
public final class TimeWarp extends Card
{
	public TimeWarp(GameState state)
	{
		super(state);

		Target target = this.addTarget(Players.instance(), "target player");

		this.addEffect(takeExtraTurns(targetedBy(target), 1, "Target player takes an extra turn after this one."));
	}
}
