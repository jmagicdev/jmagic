package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Quest for Ula's Temple")
@Types({Type.ENCHANTMENT})
@ManaCost("U")
@Printings({@Printings.Printed(ex = Expansion.WORLDWAKE, r = Rarity.RARE)})
@ColorIdentity({Color.BLUE})
public final class QuestforUlasTemple extends Card
{
	/**
	 * @eparam CAUSE: quest for ula's temple's second ability
	 * @eparam OBJECT: the object to reveal
	 * @eparam RESULT: empty
	 */
	public static final EventType REVEAL_AND_QUEST_COUNTER = new EventType("REVEAL_AND_QUEST_COUNTER")
	{
		@Override
		public Parameter affects()
		{
			return null;
		}

		@Override
		public boolean perform(Game game, Event event, java.util.Map<Parameter, Set> parameters)
		{
			Set cause = parameters.get(Parameter.CAUSE);
			Set card = parameters.get(Parameter.OBJECT);

			java.util.Map<Parameter, Set> revealParameters = new java.util.HashMap<Parameter, Set>();
			revealParameters.put(Parameter.CAUSE, cause);
			revealParameters.put(Parameter.OBJECT, card);
			Event reveal = createEvent(game, "Reveal " + card, EventType.REVEAL, revealParameters);
			reveal.perform(event, true);

			Event counters = putCountersOnThis(1, Counter.CounterType.QUEST, "Quest for Ula's Temple").createEvent(game, event.getSource());
			counters.perform(event, true);

			event.setResult(Empty.set);
			return false;
		}
	};

	public static final class QuestforUlasTempleAbility0 extends EventTriggeredAbility
	{
		public QuestforUlasTempleAbility0(GameState state)
		{
			super(state, "At the beginning of your upkeep, you may look at the top card of your library. If it's a creature card, you may reveal it and put a quest counter on Quest for Ula's Temple.");
			this.addPattern(atTheBeginningOfYourUpkeep());

			SetGenerator topCard = TopCards.instance(1, LibraryOf.instance(You.instance()));

			EventFactory look = new EventFactory(EventType.LOOK, "Look at the top card of your library");
			look.parameters.put(EventType.Parameter.CAUSE, This.instance());
			look.parameters.put(EventType.Parameter.OBJECT, topCard);
			look.parameters.put(EventType.Parameter.PLAYER, You.instance());
			EventFactory youMayLook = youMay(look, "You may look at the top card of your libary");

			EventFactory revealAndCounter = new EventFactory(REVEAL_AND_QUEST_COUNTER, "Reveal the top card of your library and put a quest counter on Quest for Ula's Temple");
			revealAndCounter.parameters.put(EventType.Parameter.CAUSE, This.instance());
			revealAndCounter.parameters.put(EventType.Parameter.OBJECT, topCard);
			EventFactory youMayReveal = youMay(revealAndCounter, "You may reveal the top card of your library and put a quest counter on Quest for Ula's Temple.");

			EventFactory ifTopCardIsCreatureYouMayReveal = new EventFactory(EventType.IF_CONDITION_THEN_ELSE, "If the top card of your library is a creature card, you may reveal it and put a quest counter on Quest for Ula's Temple.");
			ifTopCardIsCreatureYouMayReveal.parameters.put(EventType.Parameter.IF, Intersect.instance(HasType.instance(Type.CREATURE), topCard));
			ifTopCardIsCreatureYouMayReveal.parameters.put(EventType.Parameter.THEN, Identity.instance(youMayReveal));

			EventFactory theWholeNineYards = new EventFactory(EventType.IF_EVENT_THEN_ELSE, "You may look at the top card of your library. If it's a creature card, you may reveal it and put a quest counter on Quest for Ula's Temple.");
			theWholeNineYards.parameters.put(EventType.Parameter.IF, Identity.instance(youMayLook));
			theWholeNineYards.parameters.put(EventType.Parameter.THEN, Identity.instance(ifTopCardIsCreatureYouMayReveal));
			this.addEffect(theWholeNineYards);
		}
	}

	public static final class QuestforUlasTempleAbility1 extends EventTriggeredAbility
	{
		public QuestforUlasTempleAbility1(GameState state)
		{
			super(state, "At the beginning of each end step, if there are three or more quest counters on Quest for Ula's Temple, you may put a Kraken, Leviathan, Octopus, or Serpent creature card from your hand onto the battlefield.");
			this.addPattern(atTheBeginningOfEachEndStep());

			this.interveningIf = Intersect.instance(Between.instance(3, null), Count.instance(CountersOn.instance(ABILITY_SOURCE_OF_THIS)));

			SetGenerator inYourHand = InZone.instance(HandOf.instance(You.instance()));
			SetGenerator generallyBigCreatures = HasSubType.instance(SubType.KRAKEN, SubType.LEVIATHAN, SubType.OCTOPUS, SubType.SERPENT);

			EventFactory putOntoBattlefield = new EventFactory(EventType.PUT_ONTO_BATTLEFIELD_CHOICE, "Put a creature card from your hand onto the battlefield");
			putOntoBattlefield.parameters.put(EventType.Parameter.CAUSE, This.instance());
			putOntoBattlefield.parameters.put(EventType.Parameter.CONTROLLER, You.instance());
			putOntoBattlefield.parameters.put(EventType.Parameter.OBJECT, Intersect.instance(inYourHand, generallyBigCreatures));

			this.addEffect(youMay(putOntoBattlefield, "You may put a Kraken, Leviathan, Octopus, or Serpent card from your hand onto the battlefield."));

		}
	}

	public QuestforUlasTemple(GameState state)
	{
		super(state);

		// At the beginning of your upkeep, you may look at the top card of your
		// library. If it's a creature card, you may reveal it and put a quest
		// counter on Quest for Ula's Temple.
		this.addAbility(new QuestforUlasTempleAbility0(state));

		// At the beginning of each end step, if there are three or more quest
		// counters on Quest for Ula's Temple, you may put a Kraken, Leviathan,
		// Octopus, or Serpent creature card from your hand onto the
		// battlefield.
		this.addAbility(new QuestforUlasTempleAbility1(state));
	}
}
