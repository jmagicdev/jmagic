package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.expansions.*;

@Name("Urban Evolution")
@Types({Type.SORCERY})
@ManaCost("3GU")
@Printings({@Printings.Printed(ex = Gatecrash.class, r = Rarity.UNCOMMON)})
@ColorIdentity({Color.BLUE, Color.GREEN})
public final class UrbanEvolution extends Card
{
	public UrbanEvolution(GameState state)
	{
		super(state);

		// Draw three cards. You may play an additional land this turn.
		this.addEffect(drawCards(You.instance(), 3, "Draw three cards."));
		this.addEffect(playExtraLands(You.instance(), 1, "You may play an additional land this turn."));
	}
}
