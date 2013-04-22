package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Merchant Scroll")
@Types({Type.SORCERY})
@ManaCost("1U")
@Printings({@Printings.Printed(ex = Expansion.EIGHTH_EDITION, r = Rarity.UNCOMMON), @Printings.Printed(ex = Expansion.HOMELANDS, r = Rarity.COMMON)})
@ColorIdentity({Color.BLUE})
public final class MerchantScroll extends Card
{
	public MerchantScroll(GameState state)
	{
		super(state);

		SetGenerator blueInstants = Intersect.instance(HasColor.instance(Color.BLUE), HasType.instance(Type.INSTANT));

		// Search your library for a blue instant card, reveal that card, and
		// put it into your hand. Then shuffle your library.
		EventFactory factory = new EventFactory(EventType.SEARCH_LIBRARY_AND_PUT_INTO, "Search your library for a blue instant card, reveal that card, and put it into your hand. Then shuffle your library.");
		factory.parameters.put(EventType.Parameter.CAUSE, This.instance());
		factory.parameters.put(EventType.Parameter.PLAYER, You.instance());
		factory.parameters.put(EventType.Parameter.NUMBER, numberGenerator(1));
		factory.parameters.put(EventType.Parameter.TO, HandOf.instance(You.instance()));
		factory.parameters.put(EventType.Parameter.TYPE, Identity.instance(blueInstants));
		this.addEffect(factory);
	}
}
