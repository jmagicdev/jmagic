package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Sunspring Expedition")
@Types({Type.ENCHANTMENT})
@ManaCost("W")
@Printings({@Printings.Printed(ex = Expansion.ZENDIKAR, r = Rarity.COMMON)})
@ColorIdentity({Color.WHITE})
public final class SunspringExpedition extends Card
{
	public static final class SpringTheSun extends ActivatedAbility
	{
		public SpringTheSun(GameState state)
		{
			super(state, "Remove three quest counters from Sunspring Expedition and sacrifice it: You gain 8 life.");
			this.addCost(removeCountersFromThis(3, Counter.CounterType.QUEST, "Sunspring Expedition"));

			this.addCost(sacrificeThis("Sunspring Expedition"));
			this.addEffect(gainLife(You.instance(), 8, "You gain 8 life."));
		}
	}

	public SunspringExpedition(GameState state)
	{
		super(state);

		// Landfall \u2014 Whenever a land enters the battlefield under your
		// control, you may put a quest counter on Sunspring Expedition.
		this.addAbility(new org.rnd.jmagic.abilities.LandfallForQuestCounter(state, this.getName()));

		// Remove three quest counters from Sunspring Expedition and sacrifice
		// it: You gain 8 life.
		this.addAbility(new SpringTheSun(state));
	}
}
