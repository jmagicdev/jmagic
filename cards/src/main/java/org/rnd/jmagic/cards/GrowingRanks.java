package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;

@Name("Growing Ranks")
@Types({Type.ENCHANTMENT})
@ManaCost("2(G/W)(G/W)")
@ColorIdentity({Color.WHITE, Color.GREEN})
public final class GrowingRanks extends Card
{
	public static final class GrowingRanksAbility0 extends EventTriggeredAbility
	{
		public GrowingRanksAbility0(GameState state)
		{
			super(state, "At the beginning of your upkeep, populate.");
			this.addPattern(atTheBeginningOfYourUpkeep());
			this.addEffect(populate());
		}
	}

	public GrowingRanks(GameState state)
	{
		super(state);

		// At the beginning of your upkeep, populate. (Put a token onto the
		// battlefield that's a copy of a creature token you control.)
		this.addAbility(new GrowingRanksAbility0(state));
	}
}
