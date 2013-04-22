package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Ior Ruin Expedition")
@Types({Type.ENCHANTMENT})
@ManaCost("1U")
@Printings({@Printings.Printed(ex = Expansion.ZENDIKAR, r = Rarity.COMMON)})
@ColorIdentity({Color.BLUE})
public final class IorRuinExpedition extends Card
{
	public static final class QuestDraw extends ActivatedAbility
	{
		public QuestDraw(GameState state)
		{
			super(state, "Remove three quest counters from Ior Ruin Expedition and sacrifice it: Draw two cards.");
			this.addCost(removeCountersFromThis(3, Counter.CounterType.QUEST, "Ior Ruin Expedition"));

			this.addCost(sacrificeThis("Ior Ruin Expedition"));
			this.addEffect(drawCards(You.instance(), 2, "Draw two cards."));
		}
	}

	public IorRuinExpedition(GameState state)
	{
		super(state);

		// Landfall \u2014 Whenever a land enters the battlefield under your
		// control, you may put a quest counter on Ior Ruin Expedition.
		this.addAbility(new org.rnd.jmagic.abilities.LandfallForQuestCounter(state, this.getName()));

		// Remove three quest counters from Ior Ruin Expedition and sacrifice
		// it: Draw two cards.
		this.addAbility(new QuestDraw(state));
	}
}
