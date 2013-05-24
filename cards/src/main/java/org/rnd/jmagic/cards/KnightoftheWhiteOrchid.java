package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Knight of the White Orchid")
@Types({Type.CREATURE})
@SubTypes({SubType.KNIGHT, SubType.HUMAN})
@ManaCost("WW")
@Printings({@Printings.Printed(ex = Expansion.SHARDS_OF_ALARA, r = Rarity.RARE)})
@ColorIdentity({Color.WHITE})
public final class KnightoftheWhiteOrchid extends Card
{
	public static final class PlainsFetch extends EventTriggeredAbility
	{
		public PlainsFetch(GameState state)
		{
			super(state, "When Knight of the White Orchid enters the battlefield, if an opponent controls more lands than you, you may search your library for a Plains card, put it onto the battlefield, then shuffle your library.");
			this.addPattern(whenThisEntersTheBattlefield());

			SetGenerator opponentsLands = Intersect.instance(ControlledBy.instance(OpponentsOf.instance(You.instance())), HasType.instance(Type.LAND));
			SetGenerator landsEachOpponentControls = SplitOnController.instance(opponentsLands);
			SetGenerator mostLands = LargestSet.instance(landsEachOpponentControls);
			SetGenerator numOpponentsLands = Count.instance(mostLands);

			SetGenerator yourLands = Intersect.instance(ControlledBy.instance(You.instance()), HasType.instance(Type.LAND));
			SetGenerator moreThanYours = Sum.instance(Union.instance(Count.instance(yourLands), numberGenerator(1)));
			SetGenerator opponentHasMore = Intersect.instance(numOpponentsLands, Between.instance(moreThanYours, Empty.instance()));
			this.interveningIf = opponentHasMore;

			EventFactory search = new EventFactory(EventType.SEARCH_LIBRARY_AND_PUT_INTO, "Search your library for a Plains card, put it onto the battlefield, then shuffle your library.");
			search.parameters.put(EventType.Parameter.CAUSE, This.instance());
			search.parameters.put(EventType.Parameter.PLAYER, You.instance());
			search.parameters.put(EventType.Parameter.CONTROLLER, You.instance());
			search.parameters.put(EventType.Parameter.NUMBER, numberGenerator(1));
			search.parameters.put(EventType.Parameter.TO, Battlefield.instance());
			search.parameters.put(EventType.Parameter.TYPE, Identity.instance(HasSubType.instance(SubType.PLAINS)));

			this.addEffect(youMay(search, "You may search your library for a Plains card, put it onto the battlefield, then shuffle your library."));
		}
	}

	public KnightoftheWhiteOrchid(GameState state)
	{
		super(state);

		this.setPower(2);
		this.setToughness(2);

		// First strike
		this.addAbility(new org.rnd.jmagic.abilities.keywords.FirstStrike(state));

		// When Knight of the White Orchid enters the battlefield, if an
		// opponent controls more lands than you, you may search your library
		// for a Plains card, put it onto the battlefield, then shuffle your
		// library.
		this.addAbility(new PlainsFetch(state));
	}
}
