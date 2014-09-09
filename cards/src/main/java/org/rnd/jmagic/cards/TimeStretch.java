package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Time Stretch")
@Types({Type.SORCERY})
@ManaCost("8UU")
@ColorIdentity({Color.BLUE})
public final class TimeStretch extends Card
{
	public TimeStretch(GameState state)
	{
		super(state);

		Target target = this.addTarget(Players.instance(), "target player");

		this.addEffect(takeExtraTurns(targetedBy(target), 2, "Target player takes two extra turns after this one."));
	}
}
