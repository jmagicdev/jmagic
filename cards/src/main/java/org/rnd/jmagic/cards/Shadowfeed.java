package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Shadowfeed")
@Types({Type.INSTANT})
@ManaCost("B")
@ColorIdentity({Color.BLACK})
public final class Shadowfeed extends Card
{
	public Shadowfeed(GameState state)
	{
		super(state);

		Target target = this.addTarget(InZone.instance(GraveyardOf.instance(Players.instance())), "target card from a graveyard");

		this.addEffect(exile(targetedBy(target), "Exile target card from a graveyard."));

		this.addEffect(gainLife(You.instance(), 3, "You gain 3 life."));
	}
}
