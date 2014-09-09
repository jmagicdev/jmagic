package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.engine.patterns.*;

@Name("Pyromancer Ascension")
@Types({Type.ENCHANTMENT})
@ManaCost("1R")
@ColorIdentity({Color.RED})
public final class PyromancerAscension extends Card
{
	public static final class QuestCounters extends EventTriggeredAbility
	{
		public QuestCounters(GameState state)
		{
			super(state, "Whenever you cast an instant or sorcery spell that has the same name as a card in your graveyard, you may put a quest counter on Pyromancer Ascension.");

			SimpleEventPattern castSomethingAgain = new SimpleEventPattern(EventType.BECOMES_PLAYED);

			SetGenerator sameName = HasName.instance(NameOf.instance(InZone.instance(GraveyardOf.instance(You.instance()))));
			SetGenerator sameNameSpells = Intersect.instance(HasType.instance(Type.INSTANT, Type.SORCERY), sameName);
			SetGenerator triggerSpells = Intersect.instance(ControlledBy.instance(You.instance(), Stack.instance()), sameNameSpells);
			castSomethingAgain.put(EventType.Parameter.OBJECT, triggerSpells);

			this.addPattern(castSomethingAgain);

			this.addEffect(youMayPutAQuestCounterOnThis("Pyromancer Ascension"));
		}
	}

	public static final class CopySpells extends EventTriggeredAbility
	{
		public CopySpells(GameState state)
		{
			super(state, "Whenever you cast an instant or sorcery spell while Pyromancer Ascension has two or more quest counters on it, you may copy that spell. You may choose new targets for the copy.");
			SetGenerator countersOnThis = Count.instance(CountersOn.instance(ABILITY_SOURCE_OF_THIS, Counter.CounterType.QUEST));
			SetGenerator twoOrMore = Between.instance(2, null);
			SetGenerator thisHasTwoCounters = Intersect.instance(countersOnThis, twoOrMore);
			this.canTrigger = Both.instance(this.canTrigger, thisHasTwoCounters);

			SimpleEventPattern castSomething = new SimpleEventPattern(EventType.BECOMES_PLAYED);
			SetGenerator triggerSpells = Intersect.instance(ControlledBy.instance(You.instance(), Stack.instance()), HasType.instance(Type.INSTANT, Type.SORCERY));
			castSomething.put(EventType.Parameter.OBJECT, triggerSpells);
			this.addPattern(castSomething);

			SetGenerator triggerEvent = TriggerEvent.instance(This.instance());
			SetGenerator spell = EventParameter.instance(triggerEvent, EventType.Parameter.OBJECT);

			EventFactory copy = new EventFactory(EventType.COPY_SPELL_OR_ABILITY, "Copy that spell and you may choose new targets for the copy");
			copy.parameters.put(EventType.Parameter.CAUSE, This.instance());
			copy.parameters.put(EventType.Parameter.OBJECT, spell);
			copy.parameters.put(EventType.Parameter.PLAYER, You.instance());

			this.addEffect(youMay(copy, "You may copy that spell. You may choose new targets for the copy."));
		}
	}

	public PyromancerAscension(GameState state)
	{
		super(state);

		// Whenever you cast an instant or sorcery spell that has the same name
		// as a card in your graveyard, you may put a quest counter on
		// Pyromancer Ascension.
		this.addAbility(new QuestCounters(state));

		// Whenever you cast an instant or sorcery spell while Pyromancer
		// Ascension has two or more quest counters on it, you may copy that
		// spell. You may choose new targets for the copy.
		this.addAbility(new CopySpells(state));
	}
}
