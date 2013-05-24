package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.gameTypes.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Academy at Tolaria West")
@Types({Type.PLANE})
@SubTypes({SubType.DOMINARIA})
@Printings({@Printings.Printed(ex = Expansion.PLANECHASE, r = Rarity.COMMON)})
@ColorIdentity({})
public final class AcademyatTolariaWest extends Card
{
	public static final class DrawSeven extends EventTriggeredAbility
	{
		public DrawSeven(GameState state)
		{
			super(state, "At the beginning of your end step, if you have no cards in hand, draw seven cards.");

			this.addPattern(atTheBeginningOfYourEndStep());

			this.interveningIf = Hellbent.instance();

			this.addEffect(drawCards(You.instance(), 7, "Draw seven cards."));

			this.canTrigger = Planechase.triggeredAbilityCanTrigger;
		}
	}

	public static final class ChaosDiscard extends EventTriggeredAbility
	{
		public ChaosDiscard(GameState state)
		{
			super(state, "Whenever you roll (C), discard your hand.");

			this.addPattern(Planechase.wheneverYouRollChaos());

			this.addEffect(discardHand(You.instance(), "Discard your hand."));

			this.canTrigger = Planechase.triggeredAbilityCanTrigger;
		}
	}

	public AcademyatTolariaWest(GameState state)
	{
		super(state);

		this.addAbility(new DrawSeven(state));

		this.addAbility(new ChaosDiscard(state));
	}
}
