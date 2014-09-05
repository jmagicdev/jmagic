package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.patterns.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.expansions.*;

@Name("Quest for the Gravelord")
@Types({Type.ENCHANTMENT})
@ManaCost("B")
@Printings({@Printings.Printed(ex = Zendikar.class, r = Rarity.UNCOMMON)})
@ColorIdentity({Color.BLACK})
public final class QuestfortheGravelord extends Card
{
	public static final class QuestCounters extends EventTriggeredAbility
	{
		public QuestCounters(GameState state)
		{
			super(state, "Whenever a creature is put into a graveyard from the battlefield, you may put a quest counter on Quest for the Gravelord.");

			this.addPattern(new SimpleZoneChangePattern(Battlefield.instance(), GraveyardOf.instance(Players.instance()), CreaturePermanents.instance(), true));

			this.addEffect(youMayPutAQuestCounterOnThis("Quest for the Gravelord"));
		}
	}

	public static final class MakeZombie extends ActivatedAbility
	{
		public MakeZombie(GameState state)
		{
			super(state, "Remove three quest counters from Quest for the Gravelord and sacrifice it: Put a 5/5 black Zombie Giant creature token onto the battlefield.");
			this.addCost(removeCountersFromThis(3, Counter.CounterType.QUEST, "Quest for the Gravelord"));
			this.addCost(sacrificeThis("Quest for the Gravelord"));

			CreateTokensFactory token = new CreateTokensFactory(1, 5, 5, "Put a 5/5 black Zombie Giant creature token onto the battlefield.");
			token.setColors(Color.BLACK);
			token.setSubTypes(SubType.ZOMBIE, SubType.GIANT);
			this.addEffect(token.getEventFactory());
		}
	}

	public QuestfortheGravelord(GameState state)
	{
		super(state);

		// Whenever a creature is put into a graveyard from the battlefield, you
		// may put a quest counter on Quest for the Gravelord.
		this.addAbility(new QuestCounters(state));

		// Remove three quest counters from Quest for the Gravelord and
		// sacrifice it: Put a 5/5 black Zombie Giant creature token onto the
		// battlefield.
		this.addAbility(new MakeZombie(state));
	}
}
