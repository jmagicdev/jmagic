package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Selective Memory")
@Types({Type.SORCERY})
@ManaCost("3U")
@Printings({@Printings.Printed(ex = Expansion.WORLDWAKE, r = Rarity.RARE)})
@ColorIdentity({Color.BLUE})
public final class SelectiveMemory extends Card
{
	public SelectiveMemory(GameState state)
	{
		super(state);

		// Search your library for any number of nonland cards and exile them.
		EventFactory search = new EventFactory(EventType.SEARCH_LIBRARY_AND_PUT_INTO, "Search your library for any number of nonland cards and exile them.");
		search.parameters.put(EventType.Parameter.CAUSE, This.instance());
		search.parameters.put(EventType.Parameter.PLAYER, You.instance());
		search.parameters.put(EventType.Parameter.NUMBER, Identity.instance(new org.rnd.util.NumberRange(0, null)));
		search.parameters.put(EventType.Parameter.TO, ExileZone.instance());
		search.parameters.put(EventType.Parameter.TYPE, Identity.instance(RelativeComplement.instance(Cards.instance(), HasType.instance(Type.LAND))));
		this.addEffect(search);

		// Then shuffle your library.
		this.addEffect(shuffleYourLibrary("Then shuffle your library."));
	}
}
