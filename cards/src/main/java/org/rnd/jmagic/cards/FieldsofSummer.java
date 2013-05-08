package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.gameTypes.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.engine.patterns.*;

@Name("Fields of Summer")
@Types({Type.PLANE})
@SubTypes({SubType.MOAG})
@Printings({@Printings.Printed(ex = Expansion.PLANECHASE, r = Rarity.COMMON)})
@ColorIdentity({})
public final class FieldsofSummer extends Card
{
	public static final class BoringAbility extends EventTriggeredAbility
	{
		public BoringAbility(GameState state)
		{
			super(state, "Whenever a player casts a spell, that player may gain 2 life.");

			SimpleEventPattern pattern = new SimpleEventPattern(EventType.BECOMES_PLAYED);
			pattern.withResult(Spells.instance());
			this.addPattern(pattern);

			SetGenerator thatPlayer = EventParameter.instance(TriggerEvent.instance(This.instance()), EventType.Parameter.PLAYER);

			this.addEffect(playerMay(thatPlayer, gainLife(thatPlayer, 2, "That player gains 2 life."), "That player may gain 2 life."));

			this.canTrigger = Planechase.triggeredAbilityCanTrigger;
		}
	}

	public static final class ThisIsTheDoorPrizeForWhenYouFailToLeaveThisBoringPlane extends EventTriggeredAbility
	{
		public ThisIsTheDoorPrizeForWhenYouFailToLeaveThisBoringPlane(GameState state)
		{
			super(state, "Whenever you roll (C), you may gain 10 life.");

			this.addPattern(Planechase.wheneverYouRollChaos());

			this.addEffect(youMay(gainLife(You.instance(), 10, "You gain 10 life."), "You may gain 10 life."));

			this.canTrigger = Planechase.triggeredAbilityCanTrigger;
		}
	}

	public FieldsofSummer(GameState state)
	{
		super(state);

		this.addAbility(new BoringAbility(state));

		this.addAbility(new ThisIsTheDoorPrizeForWhenYouFailToLeaveThisBoringPlane(state));
	}
}
