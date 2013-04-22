package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("See Beyond")
@Types({Type.SORCERY})
@ManaCost("1U")
@Printings({@Printings.Printed(ex = Expansion.RISE_OF_THE_ELDRAZI, r = Rarity.COMMON)})
@ColorIdentity({Color.BLUE})
public final class SeeBeyond extends Card
{
	public SeeBeyond(GameState state)
	{
		super(state);

		// Draw two cards, then shuffle a card from your hand into your library.
		this.addEffect(drawCards(You.instance(), 2, "Draw two cards,"));

		EventFactory shuffle = new EventFactory(EventType.SHUFFLE_INTO_LIBRARY_CHOICE, "then shuffle a card from your hand into your library.");
		shuffle.parameters.put(EventType.Parameter.CAUSE, This.instance());
		shuffle.parameters.put(EventType.Parameter.PLAYER, You.instance());
		shuffle.parameters.put(EventType.Parameter.NUMBER, numberGenerator(1));
		shuffle.parameters.put(EventType.Parameter.CHOICE, InZone.instance(HandOf.instance(You.instance())));
		this.addEffect(shuffle);
	}
}
