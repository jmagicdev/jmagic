package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.expansions.*;

@Name("Concentrate")
@Types({Type.SORCERY})
@ManaCost("2UU")
@Printings({@Printings.Printed(ex = EighthEdition.class, r = Rarity.UNCOMMON), @Printings.Printed(ex = Odyssey.class, r = Rarity.UNCOMMON)})
@ColorIdentity({Color.BLUE})
public final class Concentrate extends Card
{
	public Concentrate(GameState state)
	{
		super(state);

		this.addEffect(drawCards(You.instance(), 3, "Draw three cards."));
	}
}
