package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;

@Name("Steady Progress")
@Types({Type.INSTANT})
@ManaCost("2U")
@Printings({@Printings.Printed(ex = Expansion.SCARS_OF_MIRRODIN, r = Rarity.COMMON)})
@ColorIdentity({Color.BLUE})
public final class SteadyProgress extends Card
{
	public SteadyProgress(GameState state)
	{
		super(state);

		// Proliferate.
		this.addEffect(proliferate());

		// Draw a card.
		this.addEffect(drawACard());
	}
}
