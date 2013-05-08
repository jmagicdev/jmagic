package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Sylvan Ranger")
@Types({Type.CREATURE})
@SubTypes({SubType.SCOUT, SubType.ELF})
@ManaCost("1G")
@Printings({@Printings.Printed(ex = Expansion.MAGIC_2011, r = Rarity.COMMON)})
@ColorIdentity({Color.GREEN})
public final class SylvanRanger extends Card
{
	public static final class SylvanRangerAbility0 extends EventTriggeredAbility
	{
		public SylvanRangerAbility0(GameState state)
		{
			super(state, "When Sylvan Ranger enters the battlefield, you may search your library for a basic land card, reveal it, put it into your hand, then shuffle your library.");
			this.addPattern(whenThisEntersTheBattlefield());

			EventFactory search = new EventFactory(EventType.SEARCH_LIBRARY_AND_PUT_INTO, "Search your library for a basic land card, reveal it, put it into your hand, then shuffle your library");
			search.parameters.put(EventType.Parameter.CAUSE, This.instance());
			search.parameters.put(EventType.Parameter.PLAYER, You.instance());
			search.parameters.put(EventType.Parameter.NUMBER, numberGenerator(1));
			search.parameters.put(EventType.Parameter.TO, HandOf.instance(You.instance()));
			search.parameters.put(EventType.Parameter.TYPE, Identity.instance(Intersect.instance(HasSuperType.instance(SuperType.BASIC), HasType.instance(Type.LAND))));
			this.addEffect(youMay(search, "You may search your library for a basic land card, reveal it, put it into your hand, then shuffle your library."));
		}
	}

	public SylvanRanger(GameState state)
	{
		super(state);

		this.setPower(1);
		this.setToughness(1);

		// When Sylvan Ranger enters the battlefield, you may search your
		// library for a basic land card, reveal it, put it into your hand, then
		// shuffle your library.
		this.addAbility(new SylvanRangerAbility0(state));
	}
}
