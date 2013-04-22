package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.gameTypes.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Lethe Lake")
@Types({Type.PLANE})
@SubTypes({SubType.ARKHOS})
@Printings({@Printings.Printed(ex = Expansion.PLANECHASE, r = Rarity.COMMON)})
@ColorIdentity({})
public final class LetheLake extends Card
{
	public static final class ProximityToACat extends EventTriggeredAbility
	{
		public ProximityToACat(GameState state)
		{
			super(state, "At the beginning of your upkeep, put the top ten cards of your library into your graveyard.");

			this.addPattern(atTheBeginningOfYourUpkeep());

			this.addEffect(millCards(You.instance(), 10, "Put the top ten cards of your library into your graveyard."));

			this.canTrigger = Planechase.triggeredAbilityCanTrigger;
		}
	}

	public static final class ChaosMill extends EventTriggeredAbility
	{
		public ChaosMill(GameState state)
		{
			super(state, "Whenever you roll (C), target player puts the top ten cards of his or her library into his or her graveyard.");

			this.addPattern(Planechase.wheneverYouRollChaos());

			Target target = this.addTarget(Players.instance(), "target player");

			this.addEffect(millCards(targetedBy(target), 10, "Target player puts the top ten cards of his or her library into his or her graveyard."));

			this.canTrigger = Planechase.triggeredAbilityCanTrigger;
		}
	}

	public LetheLake(GameState state)
	{
		super(state);

		this.addAbility(new ProximityToACat(state));

		this.addAbility(new ChaosMill(state));
	}
}
