package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.expansions.*;
import org.rnd.jmagic.engine.patterns.*;

@Name("Quest for Ancient Secrets")
@Types({Type.ENCHANTMENT})
@ManaCost("U")
@Printings({@Printings.Printed(ex = Zendikar.class, r = Rarity.UNCOMMON)})
@ColorIdentity({Color.BLUE})
public final class QuestforAncientSecrets extends Card
{
	public static final class AncientQuestCounters extends EventTriggeredAbility
	{
		public AncientQuestCounters(GameState state)
		{
			super(state, "Whenever a card is put into your graveyard from anywhere, you may put a quest counter on Quest for Ancient Secrets.");

			this.addPattern(new SimpleZoneChangePattern(null, GraveyardOf.instance(You.instance()), Cards.instance(), false));

			this.addEffect(youMayPutAQuestCounterOnThis("Quest for Ancient Secrets"));
		}
	}

	public static final class ShittiestQuestEver extends ActivatedAbility
	{
		public ShittiestQuestEver(GameState state)
		{
			super(state, "Remove five quest counters from Quest for Ancient Secrets and sacrifice it: Target player shuffles his or her graveyard into his or her library.");
			this.addCost(removeCountersFromThis(5, Counter.CounterType.QUEST, "Quest for Ancient Secrets"));

			this.addCost(sacrificeThis("Quest for Ancient Secrets"));

			Target target = this.addTarget(Players.instance(), "target player");

			EventFactory shuffle = new EventFactory(EventType.SHUFFLE_INTO_LIBRARY, "Target player shuffles his or her graveyard into his or her library.");
			shuffle.parameters.put(EventType.Parameter.CAUSE, This.instance());
			shuffle.parameters.put(EventType.Parameter.OBJECT, Union.instance(InZone.instance(GraveyardOf.instance(targetedBy(target))), targetedBy(target)));
			this.addEffect(shuffle);
		}
	}

	public QuestforAncientSecrets(GameState state)
	{
		super(state);

		// Whenever a card is put into your graveyard from anywhere, you may put
		// a quest counter on Quest for Ancient Secrets.
		this.addAbility(new AncientQuestCounters(state));

		// Remove five quest counters from Quest for Ancient Secrets and
		// sacrifice it: Target player shuffles his or her graveyard into his or
		// her library.
		this.addAbility(new ShittiestQuestEver(state));
	}
}
