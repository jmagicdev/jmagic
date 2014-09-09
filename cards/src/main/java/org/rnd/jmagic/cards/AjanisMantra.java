package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Ajani's Mantra")
@Types({Type.ENCHANTMENT})
@ManaCost("1W")
@ColorIdentity({Color.WHITE})
public final class AjanisMantra extends Card
{
	public static final class AjanisMantraAbility0 extends EventTriggeredAbility
	{
		public AjanisMantraAbility0(GameState state)
		{
			super(state, "At the beginning of your upkeep, you may gain 1 life.");
			this.addPattern(atTheBeginningOfYourUpkeep());
			this.addEffect(youMay(gainLife(You.instance(), 1, "Gain 1 life."), "You may gain 1 life."));
		}
	}

	public AjanisMantra(GameState state)
	{
		super(state);

		// At the beginning of your upkeep, you may gain 1 life.
		this.addAbility(new AjanisMantraAbility0(state));
	}
}
