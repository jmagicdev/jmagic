package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Imperial Recruiter")
@Types({Type.CREATURE})
@SubTypes({SubType.ADVISOR, SubType.HUMAN})
@ManaCost("2R")
@Printings({@Printings.Printed(ex = Expansion.PORTAL_THREE_KINGDOMS, r = Rarity.UNCOMMON)})
@ColorIdentity({Color.RED})
public final class ImperialRecruiter extends Card
{
	public static final class ImperialRecruiterAbility0 extends EventTriggeredAbility
	{
		public ImperialRecruiterAbility0(GameState state)
		{
			super(state, "When Imperial Recruiter enters the battlefield, search your library for a creature card with power 2 or less, reveal it, and put it into your hand. Then shuffle your library.");
			this.addPattern(whenThisEntersTheBattlefield());

			EventFactory factory = new EventFactory(EventType.SEARCH_LIBRARY_AND_PUT_INTO, "Search your library for a creature card with power 2 or less, reveal it, and put it into your hand. Then shuffle your library.");
			factory.parameters.put(EventType.Parameter.CAUSE, This.instance());
			factory.parameters.put(EventType.Parameter.PLAYER, You.instance());
			factory.parameters.put(EventType.Parameter.NUMBER, numberGenerator(1));
			factory.parameters.put(EventType.Parameter.TO, HandOf.instance(You.instance()));
			factory.parameters.put(EventType.Parameter.TYPE, Identity.instance(Intersect.instance(HasType.instance(Type.CREATURE), HasPower.instance(Between.instance(null, 2)))));
			this.addEffect(factory);
		}
	}

	public ImperialRecruiter(GameState state)
	{
		super(state);

		this.setPower(1);
		this.setToughness(1);

		// When Imperial Recruiter enters the battlefield, search your library
		// for a creature card with power 2 or less, reveal it, and put it into
		// your hand. Then shuffle your library.
		this.addAbility(new ImperialRecruiterAbility0(state));
	}
}
