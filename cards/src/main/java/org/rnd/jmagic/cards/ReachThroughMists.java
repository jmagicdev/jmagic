package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.expansions.*;

@Name("Reach Through Mists")
@Types({Type.INSTANT})
@SubTypes({SubType.ARCANE})
@ManaCost("U")
@Printings({@Printings.Printed(ex = ChampionsOfKamigawa.class, r = Rarity.COMMON)})
@ColorIdentity({Color.BLUE})
public final class ReachThroughMists extends Card
{
	public ReachThroughMists(GameState state)
	{
		super(state);

		// Draw a card.
		this.addEffect(drawACard());
	}
}
