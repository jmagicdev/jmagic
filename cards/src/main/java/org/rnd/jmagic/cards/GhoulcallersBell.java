package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.expansions.*;

@Name("Ghoulcaller's Bell")
@Types({Type.ARTIFACT})
@ManaCost("1")
@Printings({@Printings.Printed(ex = Innistrad.class, r = Rarity.COMMON)})
@ColorIdentity({})
public final class GhoulcallersBell extends Card
{
	public static final class GhoulcallersBellAbility0 extends ActivatedAbility
	{
		public GhoulcallersBellAbility0(GameState state)
		{
			super(state, "(T): Each player puts the top card of his or her library into his or her graveyard.");
			this.costsTap = true;
			this.addEffect(millCards(Players.instance(), 1, "Each player puts the top card of his or her library into his or her graveyard."));
		}
	}

	public GhoulcallersBell(GameState state)
	{
		super(state);

		// (T): Each player puts the top card of his or her library into his or
		// her graveyard.
		this.addAbility(new GhoulcallersBellAbility0(state));
	}
}
