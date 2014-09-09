package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Hellfire Mongrel")
@Types({Type.CREATURE})
@SubTypes({SubType.ELEMENTAL, SubType.HOUND})
@ManaCost("2R")
@ColorIdentity({Color.RED})
public final class HellfireMongrel extends Card
{
	public static final class DamageTrigger extends EventTriggeredAbility
	{
		public DamageTrigger(GameState state)
		{
			super(state, "At the beginning of each opponent's upkeep, if that player has two or fewer cards in hand, Hellfire Mongrel deals 2 damage to him or her.");
			this.addPattern(atTheBeginningOfEachOpponentsUpkeeps());

			SetGenerator twoOrFewer = Between.instance(null, 2);
			SetGenerator upkeep = EventParameter.instance(TriggerEvent.instance(This.instance()), EventType.Parameter.STEP);
			SetGenerator thatPlayer = OwnerOf.instance(upkeep);
			SetGenerator cardsInHand = Count.instance(InZone.instance(HandOf.instance(thatPlayer)));
			this.interveningIf = Intersect.instance(twoOrFewer, cardsInHand);

			this.addEffect(permanentDealDamage(2, thatPlayer, "Hellfire Mongrel deals 2 damage to that player."));
		}
	}

	public HellfireMongrel(GameState state)
	{
		super(state);

		this.setPower(2);
		this.setToughness(2);

		// At the beginning of each opponent's upkeep, if that player has two or
		// fewer cards in hand, Hellfire Mongrel deals 2 damage to him or her.
		this.addAbility(new DamageTrigger(state));
	}
}
