package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.expansions.*;

@Name("Squadron Hawk")
@Types({Type.CREATURE})
@SubTypes({SubType.BIRD})
@ManaCost("1W")
@Printings({@Printings.Printed(ex = Magic2011.class, r = Rarity.COMMON)})
@ColorIdentity({Color.WHITE})
public final class SquadronHawk extends Card
{
	public static final class SquadronHawkAbility1 extends EventTriggeredAbility
	{
		public SquadronHawkAbility1(GameState state)
		{
			super(state, "When Squadron Hawk enters the battlefield, you may search your library for up to three cards named Squadron Hawk, reveal them, put them into your hand, then shuffle your library.");
			this.addPattern(whenThisEntersTheBattlefield());

			EventFactory search = new EventFactory(EventType.SEARCH_LIBRARY_AND_PUT_INTO, "Search your library for up to three cards named Squadron Hawk, reveal them, put them into your hand, then shuffle your library");
			search.parameters.put(EventType.Parameter.CAUSE, This.instance());
			search.parameters.put(EventType.Parameter.PLAYER, You.instance());
			search.parameters.put(EventType.Parameter.NUMBER, Between.instance(null, 3));
			search.parameters.put(EventType.Parameter.TO, HandOf.instance(You.instance()));
			search.parameters.put(EventType.Parameter.TYPE, Identity.instance(HasName.instance("Squadron Hawk")));
			this.addEffect(youMay(search, "You may search your library for up to three cards named Squadron Hawk, reveal them, put them into your hand, then shuffle your library."));
		}
	}

	public SquadronHawk(GameState state)
	{
		super(state);

		this.setPower(1);
		this.setToughness(1);

		// Flying
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Flying(state));

		// When Squadron Hawk enters the battlefield, you may search your
		// library for up to three cards named Squadron Hawk, reveal them, put
		// them into your hand, then shuffle your library.
		this.addAbility(new SquadronHawkAbility1(state));
	}
}
