package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Captured Sunlight")
@Types({Type.SORCERY})
@ManaCost("2GW")
@ColorIdentity({Color.WHITE, Color.GREEN})
public final class CapturedSunlight extends Card
{
	public CapturedSunlight(GameState state)
	{
		super(state);

		// Cascade
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Cascade(state));

		// You gain 4 life.
		this.addEffect(gainLife(You.instance(), 4, "You gain 4 life."));
	}
}
