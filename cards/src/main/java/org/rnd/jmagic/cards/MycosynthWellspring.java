package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.expansions.*;

@Name("Mycosynth Wellspring")
@Types({Type.ARTIFACT})
@ManaCost("2")
@Printings({@Printings.Printed(ex = NewPhyrexia.class, r = Rarity.COMMON)})
@ColorIdentity({})
public final class MycosynthWellspring extends Card
{
	public static final class MycosynthWellspringAbility0 extends EventTriggeredAbility
	{
		public MycosynthWellspringAbility0(GameState state)
		{
			super(state, "When Mycosynth Wellspring enters the battlefield or is put into a graveyard from the battlefield, you may search your library for a basic land card, reveal it, put it into your hand, then shuffle your library.");
			this.addPattern(whenThisEntersTheBattlefield());
			this.addPattern(whenThisIsPutIntoAGraveyardFromTheBattlefield());

			EventFactory search = new EventFactory(EventType.SEARCH_LIBRARY_AND_PUT_INTO, "Search your library for a basic land card, reveal it, put it into your hand, then shuffle your library.");
			search.parameters.put(EventType.Parameter.CAUSE, This.instance());
			search.parameters.put(EventType.Parameter.PLAYER, You.instance());
			search.parameters.put(EventType.Parameter.NUMBER, numberGenerator(1));
			search.parameters.put(EventType.Parameter.TYPE, Identity.instance(Intersect.instance(HasSuperType.instance(SuperType.BASIC), HasType.instance(Type.LAND))));
			search.parameters.put(EventType.Parameter.TO, HandOf.instance(You.instance()));
			this.addEffect(youMay(search, "You may search your library for a basic land card, reveal it, put it into your hand, then shuffle your library."));
		}
	}

	public MycosynthWellspring(GameState state)
	{
		super(state);

		// When Mycosynth Wellspring enters the battlefield or is put into a
		// graveyard from the battlefield, you may search your library for a
		// basic land card, reveal it, put it into your hand, then shuffle your
		// library.
		this.addAbility(new MycosynthWellspringAbility0(state));
	}
}
