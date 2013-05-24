package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Ponder")
@Types({Type.SORCERY})
@ManaCost("U")
@Printings({@Printings.Printed(ex = Expansion.MAGIC_2012, r = Rarity.COMMON), @Printings.Printed(ex = Expansion.MAGIC_2010, r = Rarity.COMMON), @Printings.Printed(ex = Expansion.LORWYN, r = Rarity.COMMON)})
@ColorIdentity({Color.BLUE})
public final class Ponder extends Card
{
	public Ponder(GameState state)
	{
		super(state);

		// Look at the top three cards of your library, then put them back in
		// any order.
		EventFactory look = new EventFactory(EventType.LOOK_AND_PUT_BACK, "Look at the top three cards of your library, then put them back in any order.");
		look.parameters.put(EventType.Parameter.CAUSE, This.instance());
		look.parameters.put(EventType.Parameter.PLAYER, You.instance());
		look.parameters.put(EventType.Parameter.NUMBER, numberGenerator(3));
		this.addEffect(look);

		// You may shuffle your library.
		EventFactory shuffle = shuffleYourLibrary("Shuffle your library.");

		this.addEffect(youMay(shuffle, "You may shuffle your library."));

		// Draw a card.
		this.addEffect(drawCards(You.instance(), 1, "\n\nDraw a card."));
	}
}
