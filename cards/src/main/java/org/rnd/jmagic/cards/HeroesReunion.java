package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Heroes' Reunion")
@Types({Type.INSTANT})
@ManaCost("GW")
@ColorIdentity({Color.WHITE, Color.GREEN})
public final class HeroesReunion extends Card
{
	public HeroesReunion(GameState state)
	{
		super(state);

		Target target = this.addTarget(Players.instance(), "target player");
		this.addEffect(gainLife(targetedBy(target), 7, "Target player gains 7 life."));
	}
}
