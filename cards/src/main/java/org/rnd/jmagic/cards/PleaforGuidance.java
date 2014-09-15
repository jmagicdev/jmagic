package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Plea for Guidance")
@Types({Type.SORCERY})
@ManaCost("5W")
@ColorIdentity({Color.WHITE})
public final class PleaforGuidance extends Card
{
	public PleaforGuidance(GameState state)
	{
		super(state);

		// Search your library for up to two enchantment cards, reveal them, and
		// put them into your hand. Then shuffle your library.

		EventFactory search = new EventFactory(EventType.SEARCH_LIBRARY_AND_PUT_INTO, "Search your library for up to two enchantment cards, reveal them, and put them into your hand. Then shuffle your library.");
		search.parameters.put(EventType.Parameter.CAUSE, This.instance());
		search.parameters.put(EventType.Parameter.PLAYER, You.instance());
		search.parameters.put(EventType.Parameter.NUMBER, numberGenerator(2));
		search.parameters.put(EventType.Parameter.TO, HandOf.instance(You.instance()));
		search.parameters.put(EventType.Parameter.TYPE, Identity.instance(HasType.instance(Type.ENCHANTMENT)));
		this.addEffect(search);

	}
}
