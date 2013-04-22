package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.engine.patterns.*;

@Name("Quest for the Nihil Stone")
@Types({Type.ENCHANTMENT})
@ManaCost("B")
@Printings({@Printings.Printed(ex = Expansion.WORLDWAKE, r = Rarity.RARE)})
@ColorIdentity({Color.BLACK})
public final class QuestfortheNihilStone extends Card
{
	public static final class QuestfortheNihilStoneAbility0 extends EventTriggeredAbility
	{
		public QuestfortheNihilStoneAbility0(GameState state)
		{
			super(state, "Whenever an opponent discards a card, you may put a quest counter on Quest for the Nihil Stone.");

			SimpleEventPattern discard = new SimpleEventPattern(EventType.DISCARD_ONE_CARD);
			discard.put(EventType.Parameter.CARD, new OwnedByPattern(OpponentsOf.instance(You.instance())));
			this.addPattern(discard);

			this.addEffect(youMayPutAQuestCounterOnThis("Quest for the Nihil Stone"));
		}
	}

	public static final class QuestfortheNihilStoneAbility1 extends EventTriggeredAbility
	{
		public QuestfortheNihilStoneAbility1(GameState state)
		{
			super(state, "At the beginning of each opponent's upkeep, if that player has no cards in hand and Quest for the Nihil Stone has two or more quest counters on it, you may have that player lose 5 life.");
			this.addPattern(atTheBeginningOfEachOpponentsUpkeeps());

			// this will probably change when we support 2hg
			SetGenerator thatPlayer = OwnerOf.instance(EventParameter.instance(TriggerEvent.instance(This.instance()), EventType.Parameter.STEP));
			SetGenerator oppIsHellbent = Intersect.instance(numberGenerator(0), Count.instance(InZone.instance(HandOf.instance(thatPlayer))));
			SetGenerator hasTwoCounters = Intersect.instance(Between.instance(2, null), Count.instance(CountersOn.instance(ABILITY_SOURCE_OF_THIS, Counter.CounterType.QUEST)));
			this.interveningIf = Both.instance(oppIsHellbent, hasTwoCounters);

			EventFactory loseLife = loseLife(thatPlayer, 5, "That player loses 5 life");
			this.addEffect(youMay(loseLife, "You may have that player lose 5 life."));
		}
	}

	public QuestfortheNihilStone(GameState state)
	{
		super(state);

		// Whenever an opponent discards a card, you may put a quest counter on
		// Quest for the Nihil Stone.
		this.addAbility(new QuestfortheNihilStoneAbility0(state));

		// At the beginning of each opponent's upkeep, if that player has no
		// cards in hand and Quest for the Nihil Stone has two or more quest
		// counters on it, you may have that player lose 5 life.
		this.addAbility(new QuestfortheNihilStoneAbility1(state));
	}
}
