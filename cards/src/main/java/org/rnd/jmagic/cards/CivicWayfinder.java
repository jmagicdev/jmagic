package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.expansions.*;

@Name("Civic Wayfinder")
@Types({Type.CREATURE})
@SubTypes({SubType.WARRIOR, SubType.DRUID, SubType.ELF})
@ManaCost("2G")
@Printings({@Printings.Printed(ex = TenthEdition.class, r = Rarity.COMMON), @Printings.Printed(ex = RavnicaCityOfGuilds.class, r = Rarity.COMMON)})
@ColorIdentity({Color.GREEN})
public final class CivicWayfinder extends Card
{
	public static final class Wayfind extends EventTriggeredAbility
	{
		public Wayfind(GameState state)
		{
			super(state, "When Civic Wayfinder enters the battlefield, you may search your library for a basic land card, reveal it, and put it into your hand. If you do, shuffle your library.");
			this.addPattern(whenThisEntersTheBattlefield());

			EventFactory search = new EventFactory(EventType.SEARCH_LIBRARY_AND_PUT_INTO, "Search your library for a basic land card, reveal it, and put it into your hand, then shuffle your library.");
			search.parameters.put(EventType.Parameter.CAUSE, This.instance());
			search.parameters.put(EventType.Parameter.PLAYER, You.instance());
			search.parameters.put(EventType.Parameter.NUMBER, numberGenerator(1));
			search.parameters.put(EventType.Parameter.TYPE, Identity.instance(Intersect.instance(HasSuperType.instance(SuperType.BASIC), HasType.instance(Type.LAND))));
			search.parameters.put(EventType.Parameter.TO, HandOf.instance(You.instance()));

			this.addEffect(youMay(search, "You may search your library for a basic land card, reveal it, and put it into your hand. If you do, shuffle your library."));
		}
	}

	public CivicWayfinder(GameState state)
	{
		super(state);

		this.setPower(2);
		this.setToughness(2);

		this.addAbility(new Wayfind(state));
	}
}
