package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.expansions.*;

@Name("Thought Scour")
@Types({Type.INSTANT})
@ManaCost("U")
@Printings({@Printings.Printed(ex = DarkAscension.class, r = Rarity.COMMON)})
@ColorIdentity({Color.BLUE})
public final class ThoughtScour extends Card
{
	public ThoughtScour(GameState state)
	{
		super(state);

		// Target player puts the top two cards of his or her library into his
		// or her graveyard.
		SetGenerator target = targetedBy(this.addTarget(Players.instance(), "target player"));
		this.addEffect(millCards(target, 2, "Target player puts the top two cards of his or her library into his or her graveyard."));

		// Draw a card.
		this.addEffect(drawACard());
	}
}
