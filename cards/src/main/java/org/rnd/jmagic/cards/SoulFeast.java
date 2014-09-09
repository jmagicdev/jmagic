package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Soul Feast")
@Types({Type.SORCERY})
@ManaCost("3BB")
@ColorIdentity({Color.BLACK})
public final class SoulFeast extends Card
{
	public SoulFeast(GameState state)
	{
		super(state);

		Target target = this.addTarget(Players.instance(), "target player");

		this.addEffect(loseLife(targetedBy(target), 4, "Target player loses 4 life"));
		this.addEffect(gainLife(You.instance(), 4, "and you gain 4 life."));
	}
}
