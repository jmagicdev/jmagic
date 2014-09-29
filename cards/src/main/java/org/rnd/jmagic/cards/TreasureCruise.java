package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Treasure Cruise")
@Types({Type.SORCERY})
@ManaCost("7U")
@ColorIdentity({Color.BLUE})
public final class TreasureCruise extends Card
{
	public TreasureCruise(GameState state)
	{
		super(state);

		// Delve (Each card you exile from your graveyard while casting this
		// spell pays for (1).)
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Delve(state));

		// Draw three cards.
		this.addEffect(drawCards(You.instance(), 3, "Draw three cards."));
	}
}
