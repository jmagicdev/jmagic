package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Tome Scour")
@Types({Type.SORCERY})
@ManaCost("U")
@Printings({@Printings.Printed(ex = Expansion.MAGIC_2011, r = Rarity.COMMON), @Printings.Printed(ex = Expansion.MAGIC_2010, r = Rarity.COMMON)})
@ColorIdentity({Color.BLUE})
public final class TomeScour extends Card
{
	public TomeScour(GameState state)
	{
		super(state);

		Target target = this.addTarget(Players.instance(), "target player");

		this.addEffect(millCards(targetedBy(target), 5, "Target player puts the top five cards of his or her library into his or her graveyard."));
	}
}
