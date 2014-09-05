package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.expansions.*;

@Name("Steady Progress")
@Types({Type.INSTANT})
@ManaCost("2U")
@Printings({@Printings.Printed(ex = ScarsOfMirrodin.class, r = Rarity.COMMON)})
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
