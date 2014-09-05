package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.expansions.*;

@Name("Totem-Guide Hartebeest")
@Types({Type.CREATURE})
@SubTypes({SubType.ANTELOPE})
@ManaCost("4W")
@Printings({@Printings.Printed(ex = RiseOfTheEldrazi.class, r = Rarity.COMMON)})
@ColorIdentity({Color.WHITE})
public final class TotemGuideHartebeest extends Card
{
	public static final class TotemGuideHartebeestAbility0 extends EventTriggeredAbility
	{
		public TotemGuideHartebeestAbility0(GameState state)
		{
			super(state, "When Totem-Guide Hartebeest enters the battlefield, you may search your library for an Aura card, reveal it, put it into your hand, then shuffle your library.");
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

	public TotemGuideHartebeest(GameState state)
	{
		super(state);

		this.setPower(2);
		this.setToughness(5);

		// When Totem-Guide Hartebeest enters the battlefield, you may search
		// your library for an Aura card, reveal it, put it into your hand, then
		// shuffle your library.
		this.addAbility(new TotemGuideHartebeestAbility0(state));
	}
}
