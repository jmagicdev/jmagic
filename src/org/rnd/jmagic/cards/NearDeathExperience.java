package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Near-Death Experience")
@Types({Type.ENCHANTMENT})
@ManaCost("2WWW")
@Printings({@Printings.Printed(ex = Expansion.RISE_OF_THE_ELDRAZI, r = Rarity.RARE)})
@ColorIdentity({Color.WHITE})
public final class NearDeathExperience extends Card
{
	public static final class NearDeathExperienceAbility0 extends EventTriggeredAbility
	{
		public NearDeathExperienceAbility0(GameState state)
		{
			super(state, "At the beginning of your upkeep, if you have exactly 1 life, you win the game.");
			this.addPattern(atTheBeginningOfYourUpkeep());

			this.interveningIf = Intersect.instance(numberGenerator(1), LifeTotalOf.instance(You.instance()));

			this.addEffect(youWinTheGame());
		}
	}

	public NearDeathExperience(GameState state)
	{
		super(state);

		// At the beginning of your upkeep, if you have exactly 1 life, you win
		// the game.
		this.addAbility(new NearDeathExperienceAbility0(state));
	}
}
