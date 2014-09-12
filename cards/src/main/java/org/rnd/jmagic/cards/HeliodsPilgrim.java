package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Heliod's Pilgrim")
@Types({Type.CREATURE})
@SubTypes({SubType.CLERIC, SubType.HUMAN})
@ManaCost("2W")
@ColorIdentity({Color.WHITE})
public final class HeliodsPilgrim extends Card
{
	public static final class HeliodsPilgrimAbility0 extends EventTriggeredAbility
	{
		public HeliodsPilgrimAbility0(GameState state)
		{
			super(state, "When Heliod's Pilgrim enters the battlefield, you may search your library for an Aura card, reveal it, put it into your hand, then shuffle your library.");
			this.addPattern(whenThisEntersTheBattlefield());

			EventFactory search = new EventFactory(EventType.SEARCH_LIBRARY_AND_PUT_INTO, "Search your library for an Aura card, reveal it, and put it into your hand, then shuffle your library");
			search.parameters.put(EventType.Parameter.CAUSE, This.instance());
			search.parameters.put(EventType.Parameter.PLAYER, You.instance());
			search.parameters.put(EventType.Parameter.NUMBER, numberGenerator(1));
			search.parameters.put(EventType.Parameter.TYPE, Identity.instance(HasSubType.instance(SubType.AURA)));
			search.parameters.put(EventType.Parameter.TO, HandOf.instance(You.instance()));
			this.addEffect(youMay(search, "You may search your library for an Aura card, reveal it, and put it into your hand, then shuffle your library."));
		}
	}

	public HeliodsPilgrim(GameState state)
	{
		super(state);

		this.setPower(1);
		this.setToughness(2);

		// When Heliod's Pilgrim enters the battlefield, you may search your
		// library for an Aura card, reveal it, put it into your hand, then
		// shuffle your library.
		this.addAbility(new HeliodsPilgrimAbility0(state));
	}
}
