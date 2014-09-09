package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Primeval Titan")
@Types({Type.CREATURE})
@SubTypes({SubType.GIANT})
@ManaCost("4GG")
@ColorIdentity({Color.GREEN})
public final class PrimevalTitan extends Card
{
	public static final class PrimevalTitanAbility1 extends EventTriggeredAbility
	{
		public PrimevalTitanAbility1(GameState state)
		{
			super(state, "Whenever Primeval Titan enters the battlefield or attacks, you may search your library for up to two land cards, put them onto the battlefield tapped, then shuffle your library.");
			this.addPattern(whenThisEntersTheBattlefield());
			this.addPattern(whenThisAttacks());

			EventFactory search = new EventFactory(EventType.SEARCH_LIBRARY_AND_PUT_INTO, "Search your library for up to two land cards, put them onto the battlefield tapped, then shuffle your library");
			search.parameters.put(EventType.Parameter.CAUSE, This.instance());
			search.parameters.put(EventType.Parameter.CONTROLLER, You.instance());
			search.parameters.put(EventType.Parameter.PLAYER, You.instance());
			search.parameters.put(EventType.Parameter.NUMBER, Identity.instance(new org.rnd.util.NumberRange(1, 2)));
			search.parameters.put(EventType.Parameter.TO, Battlefield.instance());
			search.parameters.put(EventType.Parameter.TAPPED, NonEmpty.instance());
			search.parameters.put(EventType.Parameter.TYPE, Identity.instance(HasType.instance(Type.LAND)));
			this.addEffect(youMay(search, "You may search your library for up to two land cards, put them onto the battlefield tapped, then shuffle your library."));
		}
	}

	public PrimevalTitan(GameState state)
	{
		super(state);

		this.setPower(6);
		this.setToughness(6);

		// Trample
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Trample(state));

		// Whenever Primeval Titan enters the battlefield or attacks, you may
		// search your library for up to two land cards, put them onto the
		// battlefield tapped, then shuffle your library.
		this.addAbility(new PrimevalTitanAbility1(state));
	}
}
