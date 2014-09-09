package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Barter in Blood")
@Types({Type.SORCERY})
@ManaCost("2BB")
@ColorIdentity({Color.BLACK})
public final class BarterinBlood extends Card
{
	public BarterinBlood(GameState state)
	{
		super(state);

		// Each player sacrifices two creatures.
		this.addEffect(sacrifice(Players.instance(), 2, CreaturePermanents.instance(), "Each player sacrifices two creatures."));
	}
}
