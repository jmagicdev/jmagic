package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Weathered Wayfarer")
@Types({Type.CREATURE})
@SubTypes({SubType.HUMAN, SubType.CLERIC, SubType.NOMAD})
@ManaCost("W")
@ColorIdentity({Color.WHITE})
public final class WeatheredWayfarer extends Card
{
	public static final class WeatheredWayfarerAbility0 extends ActivatedAbility
	{
		public WeatheredWayfarerAbility0(GameState state)
		{
			super(state, "(W), (T): Search your library for a land card, reveal it, and put it into your hand. Then shuffle your library. Activate this ability only if an opponent controls more lands than you.");
			this.setManaCost(new ManaPool("(W)"));
			this.costsTap = true;

			EventFactory factory = new EventFactory(EventType.SEARCH_LIBRARY_AND_PUT_INTO, "Search your library for a land card, reveal it, and put it into your hand. Then shuffle your library.");
			factory.parameters.put(EventType.Parameter.CAUSE, This.instance());
			factory.parameters.put(EventType.Parameter.PLAYER, You.instance());
			factory.parameters.put(EventType.Parameter.NUMBER, numberGenerator(1));
			factory.parameters.put(EventType.Parameter.TO, HandOf.instance(You.instance()));
			factory.parameters.put(EventType.Parameter.TYPE, Identity.instance(HasType.instance(Type.LAND)));

			this.addEffect(factory);

			SetGenerator opponentsLands = Intersect.instance(ControlledBy.instance(OpponentsOf.instance(You.instance())), HasType.instance(Type.LAND));
			SetGenerator landsEachOpponentControls = SplitOnController.instance(opponentsLands);
			SetGenerator mostLands = LargestSet.instance(landsEachOpponentControls);
			SetGenerator numOpponentsLands = Count.instance(mostLands);

			SetGenerator yourLands = Intersect.instance(ControlledBy.instance(You.instance()), HasType.instance(Type.LAND));
			SetGenerator moreThanYours = Sum.instance(Union.instance(Count.instance(yourLands), numberGenerator(1)));
			SetGenerator opponentHasMore = Intersect.instance(numOpponentsLands, Between.instance(moreThanYours, Empty.instance()));

			this.addActivateRestriction(Not.instance(opponentHasMore));
		}
	}

	public WeatheredWayfarer(GameState state)
	{
		super(state);

		this.setPower(1);
		this.setToughness(1);

		// (W), (T): Search your library for a land card, reveal it, and put it
		// into your hand. Then shuffle your library. Activate this ability only
		// if an opponent controls more lands than you.
		this.addAbility(new WeatheredWayfarerAbility0(state));
	}
}
