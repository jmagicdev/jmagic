package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Pilgrim's Eye")
@Types({Type.ARTIFACT, Type.CREATURE})
@SubTypes({SubType.THOPTER})
@ManaCost("3")
@ColorIdentity({})
public final class PilgrimsEye extends Card
{
	public static final class ETBGetLand extends EventTriggeredAbility
	{
		public ETBGetLand(GameState state)
		{
			super(state, "When Pilgrim's Eye enters the battlefield, you may search your library for a basic land card, reveal it, put it into your hand, then shuffle your library.");
			this.addPattern(whenThisEntersTheBattlefield());

			EventFactory search = new EventFactory(EventType.SEARCH_LIBRARY_AND_PUT_INTO, "Search your library for a basic land card, reveal it, and put it into your hand, then shuffle your library.");
			search.parameters.put(EventType.Parameter.CAUSE, This.instance());
			search.parameters.put(EventType.Parameter.PLAYER, You.instance());
			search.parameters.put(EventType.Parameter.NUMBER, numberGenerator(1));
			search.parameters.put(EventType.Parameter.TYPE, Identity.instance(Intersect.instance(HasSuperType.instance(SuperType.BASIC), HasType.instance(Type.LAND))));
			search.parameters.put(EventType.Parameter.TO, HandOf.instance(You.instance()));
			this.addEffect(youMay(search, "You may search your library for a basic land card, reveal it, put it into your hand, then shuffle your library."));
		}
	}

	public PilgrimsEye(GameState state)
	{
		super(state);

		this.setPower(1);
		this.setToughness(1);

		// Flying
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Flying(state));

		// When Pilgrim's Eye enters the battlefield, you may search your
		// library for a basic land card, reveal it, put it into your hand, then
		// shuffle your library.
		this.addAbility(new ETBGetLand(state));
	}
}
