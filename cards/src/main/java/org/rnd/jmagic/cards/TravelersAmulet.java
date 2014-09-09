package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Traveler's Amulet")
@Types({Type.ARTIFACT})
@ManaCost("1")
@ColorIdentity({})
public final class TravelersAmulet extends Card
{
	public static final class TravelersAmuletAbility0 extends ActivatedAbility
	{
		public TravelersAmuletAbility0(GameState state)
		{
			super(state, "(1), Sacrifice Traveler's Amulet: Search your library for a basic land card, reveal it, and put it into your hand. Then shuffle your library.");
			this.setManaCost(new ManaPool("(1)"));
			this.addCost(sacrificeThis("Traveler's Amulet"));

			EventFactory search = new EventFactory(EventType.SEARCH_LIBRARY_AND_PUT_INTO, "Search your library for a basic land card, reveal it, and put it into your hand. Then shuffle your library.");
			search.parameters.put(EventType.Parameter.CAUSE, This.instance());
			search.parameters.put(EventType.Parameter.PLAYER, You.instance());
			search.parameters.put(EventType.Parameter.NUMBER, numberGenerator(1));
			search.parameters.put(EventType.Parameter.TYPE, Identity.instance(Intersect.instance(HasSuperType.instance(SuperType.BASIC), HasType.instance(Type.LAND))));
			search.parameters.put(EventType.Parameter.TO, HandOf.instance(You.instance()));
			this.addEffect(search);
		}
	}

	public TravelersAmulet(GameState state)
	{
		super(state);

		// (1), Sacrifice Traveler's Amulet: Search your library for a basic
		// land card, reveal it, and put it into your hand. Then shuffle your
		// library.
		this.addAbility(new TravelersAmuletAbility0(state));
	}
}
