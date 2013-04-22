package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Mind Unbound")
@Types({Type.ENCHANTMENT})
@ManaCost("4UU")
@Printings({@Printings.Printed(ex = Expansion.MAGIC_2012, r = Rarity.RARE)})
@ColorIdentity({Color.BLUE})
public final class MindUnbound extends Card
{
	public static final class MindUnboundAbility0 extends EventTriggeredAbility
	{
		public MindUnboundAbility0(GameState state)
		{
			super(state, "At the beginning of your upkeep, put a lore counter on Mind Unbound, then draw a card for each lore counter on Mind Unbound.");
			this.addPattern(atTheBeginningOfYourUpkeep());
			this.addEffect(putCountersOnThis(1, Counter.CounterType.LORE, "Put a lore counter on Mind Unbound,"));
			this.addEffect(drawCards(You.instance(), Count.instance(CountersOn.instance(ABILITY_SOURCE_OF_THIS, Counter.CounterType.LORE)), "then draw a card for each lore counter on Mind Unbound."));
		}
	}

	public MindUnbound(GameState state)
	{
		super(state);

		// At the beginning of your upkeep, put a lore counter on Mind Unbound,
		// then draw a card for each lore counter on Mind Unbound.
		this.addAbility(new MindUnboundAbility0(state));
	}
}
