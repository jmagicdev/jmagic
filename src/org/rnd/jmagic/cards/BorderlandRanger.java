package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Borderland Ranger")
@Types({Type.CREATURE})
@SubTypes({SubType.HUMAN, SubType.SCOUT})
@ManaCost("2G")
@Printings({@Printings.Printed(ex = Expansion.AVACYN_RESTORED, r = Rarity.COMMON), @Printings.Printed(ex = Expansion.MAGIC_2010, r = Rarity.COMMON)})

@ColorIdentity({Color.GREEN})
public final class BorderlandRanger extends Card
{
	public static final class SearchForLand extends EventTriggeredAbility
	{
		public SearchForLand(GameState state)
		{
			super(state, "When Borderland Ranger enters the battlefield, you may search your library for a basic land card, reveal it, and put it into your hand. If you do, shuffle your library.");
			this.addPattern(whenThisEntersTheBattlefield());

			// you may search your library for a basic land card, reveal it, and
			// put it into your hand. If you do, shuffle your library.
			EventFactory search = new EventFactory(EventType.SEARCH_LIBRARY_AND_PUT_INTO, "Search your library for a basic land card, reveal it, and put it into your hand, then shuffle your library.");
			search.parameters.put(EventType.Parameter.CAUSE, This.instance());
			search.parameters.put(EventType.Parameter.PLAYER, You.instance());
			search.parameters.put(EventType.Parameter.NUMBER, numberGenerator(1));
			search.parameters.put(EventType.Parameter.TYPE, Identity.instance(Intersect.instance(HasSuperType.instance(SuperType.BASIC), HasType.instance(Type.LAND))));
			search.parameters.put(EventType.Parameter.TO, HandOf.instance(You.instance()));

			this.addEffect(youMay(search, "You may search your library for a basic land card, reveal it, and put it into your hand. If you do, shuffle your library."));
		}
	}

	public BorderlandRanger(GameState state)
	{
		super(state);

		this.setPower(2);
		this.setToughness(2);

		this.addAbility(new SearchForLand(state));
	}
}
