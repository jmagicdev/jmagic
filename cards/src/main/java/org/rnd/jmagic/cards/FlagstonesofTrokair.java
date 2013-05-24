package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Flagstones of Trokair")
@SuperTypes({SuperType.LEGENDARY})
@Types({Type.LAND})
@Printings({@Printings.Printed(ex = Expansion.TIME_SPIRAL, r = Rarity.RARE)})
@ColorIdentity({Color.WHITE})
public final class FlagstonesofTrokair extends Card
{
	public static final class FetchPlains extends EventTriggeredAbility
	{
		public FetchPlains(GameState state)
		{
			super(state, "When Flagstones of Trokair is put into a graveyard from the battlefield, you may search your library for a Plains card and put it onto the battlefield tapped. If you do, shuffle your library.");
			this.addPattern(whenThisIsPutIntoAGraveyardFromTheBattlefield());

			EventFactory search = new EventFactory(EventType.SEARCH_LIBRARY_AND_PUT_INTO, "Search your library for a Plains card, put it onto the battlefield tapped, then shuffle your library.");
			search.parameters.put(EventType.Parameter.CAUSE, This.instance());
			search.parameters.put(EventType.Parameter.CONTROLLER, You.instance());
			search.parameters.put(EventType.Parameter.PLAYER, You.instance());
			search.parameters.put(EventType.Parameter.NUMBER, numberGenerator(1));
			search.parameters.put(EventType.Parameter.TYPE, Identity.instance(HasSubType.instance(SubType.PLAINS)));
			search.parameters.put(EventType.Parameter.TO, Battlefield.instance());
			search.parameters.put(EventType.Parameter.TAPPED, Empty.instance());
			this.addEffect(youMay(search, "You may search your library for a Plains card, put it onto the battlefield tapped, then shuffle your library."));
		}
	}

	public FlagstonesofTrokair(GameState state)
	{
		super(state);

		// (T): Add (W) to your mana pool.
		this.addAbility(new org.rnd.jmagic.abilities.TapForW(state));

		// When Flagstones of Trokair is put into a graveyard from the
		// battlefield, you may search your library for a Plains card and put it
		// onto the battlefield tapped. If you do, shuffle your library.
		this.addAbility(new FetchPlains(state));
	}
}
