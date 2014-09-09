package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.engine.patterns.*;
import org.rnd.jmagic.gameTypes.*;

@Name("The Dark Barony")
@Types({Type.PLANE})
@SubTypes({SubType.ULGROTHA})
@ColorIdentity({})
public final class TheDarkBarony extends Card
{
	public static final class Racism extends EventTriggeredAbility
	{
		public Racism(GameState state)
		{
			super(state, "Whenever a nonblack card is put into a player's graveyard from anywhere, that player loses 1 life.");

			this.addPattern(new SimpleZoneChangePattern(null, GraveyardOf.instance(Players.instance()), RelativeComplement.instance(Cards.instance(), HasColor.instance(Color.BLACK)), false));

			SetGenerator thatPlayer = OwnerOf.instance(DestinationZoneOf.instance(TriggerZoneChange.instance(This.instance())));

			this.addEffect(loseLife(thatPlayer, 1, "That player loses 1 life."));

			this.canTrigger = PlanechaseGameRules.triggeredAbilityCanTrigger;
		}
	}

	public static final class PromoteRacism extends EventTriggeredAbility
	{
		public PromoteRacism(GameState state)
		{
			super(state, "Whenever you roll (C), each opponent discards a card.");

			this.addPattern(PlanechaseGameRules.wheneverYouRollChaos());

			this.addEffect(discardCards(OpponentsOf.instance(You.instance()), 1, "Each opponent discards a card."));

			this.canTrigger = PlanechaseGameRules.triggeredAbilityCanTrigger;
		}
	}

	public TheDarkBarony(GameState state)
	{
		super(state);

		this.addAbility(new Racism(state));

		this.addAbility(new PromoteRacism(state));
	}
}
