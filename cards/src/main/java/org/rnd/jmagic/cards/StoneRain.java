package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Stone Rain")
@Types({Type.SORCERY})
@ManaCost("2R")
@ColorIdentity({Color.RED})
public final class StoneRain extends Card
{
	public StoneRain(GameState state)
	{
		super(state);

		// Destroy target land.
		Target target = this.addTarget(LandPermanents.instance(), "target land");
		this.addEffect(destroy(targetedBy(target), "Destroy target land."));
	}
}
