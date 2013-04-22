package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Entrails Feaster")
@Types({Type.CREATURE})
@SubTypes({SubType.ZOMBIE, SubType.CAT})
@ManaCost("B")
@Printings({@Printings.Printed(ex = Expansion.ONSLAUGHT, r = Rarity.RARE)})
@ColorIdentity({Color.BLACK})
public final class EntrailsFeaster extends Card
{
	public static final class Feast extends EventTriggeredAbility
	{
		public Feast(GameState state)
		{
			super(state, "At the beginning of your upkeep, you may exile a creature card from a graveyard. If you do, put a +1/+1 counter on Entrails Feaster. If you don't, tap Entrails Feaster.");
			this.addPattern(atTheBeginningOfYourUpkeep());

			SetGenerator creatureCardsInGraveyard = Intersect.instance(HasType.instance(Type.CREATURE), InZone.instance(GraveyardOf.instance(Players.instance())));
			EventFactory exile = exile(You.instance(), creatureCardsInGraveyard, 1, "Exile a creature card from a graveyard");

			EventFactory ifFactory = new EventFactory(EventType.IF_EVENT_THEN_ELSE, "You may exile a creature card from a graveyard. If you do, put a +1/+1 counter on Entrails Feaster. If you don't, tap Entrails Feaster.");
			ifFactory.parameters.put(EventType.Parameter.IF, Identity.instance(youMay(exile, "You may exile a creature card from a graveyard.")));
			ifFactory.parameters.put(EventType.Parameter.THEN, Identity.instance(putCountersOnThis(1, Counter.CounterType.PLUS_ONE_PLUS_ONE, "Entrails Feaster")));
			ifFactory.parameters.put(EventType.Parameter.ELSE, Identity.instance(tap(ABILITY_SOURCE_OF_THIS, "Tap Entrails Feaster.")));
			this.addEffect(ifFactory);
		}
	}

	public EntrailsFeaster(GameState state)
	{
		super(state);

		this.setPower(1);
		this.setToughness(1);

		// At the beginning of your upkeep, you may exile a creature card from a
		// graveyard. If you do, put a +1/+1 counter on Entrails Feaster. If you
		// don't, tap Entrails Feaster.
		this.addAbility(new Feast(state));
	}
}
