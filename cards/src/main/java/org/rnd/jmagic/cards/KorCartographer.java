package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Kor Cartographer")
@Types({Type.CREATURE})
@SubTypes({SubType.KOR, SubType.SCOUT})
@ManaCost("3W")
@ColorIdentity({Color.WHITE})
public final class KorCartographer extends Card
{
	public static final class GetPlains extends EventTriggeredAbility
	{
		public GetPlains(GameState state)
		{
			super(state, "When Kor Cartographer enters the battlefield, you may search your library for a Plains card, put it onto the battlefield tapped, then shuffle your library.");
			this.addPattern(whenThisEntersTheBattlefield());

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

	public KorCartographer(GameState state)
	{
		super(state);

		this.setPower(2);
		this.setToughness(2);

		// When Kor Cartographer enters the battlefield, you may search your
		// library for a Plains card, put it onto the battlefield tapped, then
		// shuffle your library.
		this.addAbility(new GetPlains(state));
	}
}
