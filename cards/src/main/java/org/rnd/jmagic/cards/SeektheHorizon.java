package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Seek the Horizon")
@Types({Type.SORCERY})
@ManaCost("3G")
@ColorIdentity({Color.GREEN})
public final class SeektheHorizon extends Card
{
	public SeektheHorizon(GameState state)
	{
		super(state);

		// Search your library for up to three basic land cards, reveal them,
		// and put them into your hand. Then shuffle your library.
		EventFactory search = new EventFactory(EventType.SEARCH_LIBRARY_AND_PUT_INTO, "Search your library for up to three basic land cards, reveal them, and put them into your hand. Then shuffle your library.");
		search.parameters.put(EventType.Parameter.CAUSE, This.instance());
		search.parameters.put(EventType.Parameter.PLAYER, You.instance());
		search.parameters.put(EventType.Parameter.NUMBER, numberGenerator(3));
		search.parameters.put(EventType.Parameter.TO, HandOf.instance(You.instance()));
		search.parameters.put(EventType.Parameter.TYPE, Identity.instance(Intersect.instance(HasSuperType.instance(SuperType.BASIC), HasType.instance(Type.LAND))));
		this.addEffect(search);
	}
}
