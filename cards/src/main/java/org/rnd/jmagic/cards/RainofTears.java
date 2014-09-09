package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Rain of Tears")
@Types({Type.SORCERY})
@ManaCost("1BB")
@ColorIdentity({Color.BLACK})
public final class RainofTears extends Card
{
	public RainofTears(GameState state)
	{
		super(state);

		Target target = this.addTarget(LandPermanents.instance(), "target land");
		this.addEffect(destroy(targetedBy(target), "Destroy target land."));
	}
}
