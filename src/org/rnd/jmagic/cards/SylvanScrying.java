package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Sylvan Scrying")
@Types({Type.SORCERY})
@ManaCost("1G")
@Printings({@Printings.Printed(ex = Expansion.TENTH_EDITION, r = Rarity.UNCOMMON), @Printings.Printed(ex = Expansion.MIRRODIN, r = Rarity.UNCOMMON)})
@ColorIdentity({Color.GREEN})
public final class SylvanScrying extends Card
{
	public SylvanScrying(GameState state)
	{
		super(state);

		// Search your library for a land card, reveal it, and put it into your
		// hand. Then shuffle your library.
		EventFactory search = new EventFactory(EventType.SEARCH_LIBRARY_AND_PUT_INTO, "Search your library for a land card, reveal it, and put it into your hand. Then shuffle your library.");
		search.parameters.put(EventType.Parameter.CAUSE, This.instance());
		search.parameters.put(EventType.Parameter.PLAYER, You.instance());
		search.parameters.put(EventType.Parameter.NUMBER, numberGenerator(1));
		search.parameters.put(EventType.Parameter.TYPE, Identity.instance(HasType.instance(Type.LAND)));
		search.parameters.put(EventType.Parameter.TO, HandOf.instance(You.instance()));
		this.addEffect(search);
	}
}
