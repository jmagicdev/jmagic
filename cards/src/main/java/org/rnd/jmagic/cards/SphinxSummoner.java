package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Sphinx Summoner")
@Types({Type.CREATURE, Type.ARTIFACT})
@SubTypes({SubType.SPHINX})
@ManaCost("3UB")
@Printings({@Printings.Printed(ex = Expansion.CONFLUX, r = Rarity.RARE)})
@ColorIdentity({Color.BLUE, Color.BLACK})
public final class SphinxSummoner extends Card
{
	public static final class ChristmasShopping extends EventTriggeredAbility
	{
		public ChristmasShopping(GameState state)
		{
			super(state, "When Sphinx Summoner enters the battlefield, you may search your library for an artifact creature card, reveal it, and put it into your hand. If you do, shuffle your library.");

			this.addPattern(whenThisEntersTheBattlefield());

			EventFactory searchFactory = new EventFactory(EventType.SEARCH_LIBRARY_AND_PUT_INTO, "Search your library for an artifact creature card, reveal it, and put it into your hand. If you do, shuffle your library.");
			searchFactory.parameters.put(EventType.Parameter.CAUSE, This.instance());
			searchFactory.parameters.put(EventType.Parameter.PLAYER, You.instance());
			searchFactory.parameters.put(EventType.Parameter.NUMBER, numberGenerator(1));
			searchFactory.parameters.put(EventType.Parameter.TO, HandOf.instance(You.instance()));
			searchFactory.parameters.put(EventType.Parameter.TYPE, Identity.instance(Intersect.instance(HasType.instance(Type.ARTIFACT), HasType.instance(Type.CREATURE))));

			this.addEffect(youMay(searchFactory, "You may search your library for an artifact creature card, reveal it, and put it into your hand. If you do, shuffle your library."));
		}
	}

	public SphinxSummoner(GameState state)
	{
		super(state);

		this.setPower(3);
		this.setToughness(3);

		this.addAbility(new org.rnd.jmagic.abilities.keywords.Flying(state));

		this.addAbility(new ChristmasShopping(state));
	}
}
