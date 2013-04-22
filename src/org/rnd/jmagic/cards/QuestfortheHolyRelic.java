package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.patterns.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Quest for the Holy Relic")
@Types({Type.ENCHANTMENT})
@ManaCost("W")
@Printings({@Printings.Printed(ex = Expansion.ZENDIKAR, r = Rarity.UNCOMMON)})
@ColorIdentity({Color.WHITE})
public final class QuestfortheHolyRelic extends Card
{
	public static final class CreatureQuest extends EventTriggeredAbility
	{
		public CreatureQuest(GameState state)
		{
			super(state, "Whenever you cast a creature spell, you may put a quest counter on Quest for the Holy Relic.");

			SimpleEventPattern youCastACreature = new SimpleEventPattern(EventType.BECOMES_PLAYED);
			SetGenerator creatureSpells = Intersect.instance(HasType.instance(Type.CREATURE), Spells.instance());
			SetGenerator yourCreatureSpells = Intersect.instance(ControlledBy.instance(You.instance(), Stack.instance()), creatureSpells);
			youCastACreature.put(EventType.Parameter.OBJECT, yourCreatureSpells);
			this.addPattern(youCastACreature);

			this.addEffect(youMayPutAQuestCounterOnThis("Quest for the Holy Relic"));
		}
	}

	/**
	 * @eparam CAUSE: quest's activated ability
	 * @eparam PLAYER: controller of CAUSE
	 * @eparam CHOICE: creatures PLAYER controls
	 */
	public static final EventType QUEST_FOR_THE_HOLY_RELIC_EVENT = new EventType("QUEST_FOR_THE_HOLY_RELIC_EVENT")
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
			Set you = parameters.get(Parameter.PLAYER);

			java.util.Map<Parameter, Set> searchParameters = new java.util.HashMap<Parameter, Set>();
			searchParameters.put(Parameter.CAUSE, cause);
			searchParameters.put(Parameter.PLAYER, you);
			searchParameters.put(Parameter.NUMBER, new Set(1));
			searchParameters.put(Parameter.TYPE, new Set(HasSubType.instance(SubType.EQUIPMENT)));
			searchParameters.put(Parameter.TO, new Set(game.actualState.battlefield()));
			searchParameters.put(Parameter.CONTROLLER, you);
			Event search = createEvent(game, "Search your library for an Equipment card and put it onto the battlefield, then shuffle your library", SEARCH_LIBRARY_AND_PUT_INTO, searchParameters);
			search.perform(event, true);

			Set thatEquipment = search.getResult();
			Set yourCreatures = parameters.get(Parameter.CHOICE);

			java.util.Map<Parameter, Set> attachParameters = new java.util.HashMap<Parameter, Set>();
			attachParameters.put(Parameter.OBJECT, thatEquipment);
			attachParameters.put(Parameter.PLAYER, you);
			attachParameters.put(Parameter.CHOICE, yourCreatures);
			Event attach = createEvent(game, "Attach that Equipment to a creature you control", ATTACH_TO_CHOICE, attachParameters);
			attach.perform(event, true);

			event.setResult(Empty.set);
			return true;
		}
	};

	public static final class FindEquipment extends ActivatedAbility
	{
		public FindEquipment(GameState state)
		{
			super(state, "Remove five quest counters from Quest for the Holy Relic and sacrifice it: Search your library for an Equipment card, put it onto the battlefield, and attach it to a creature you control. Then shuffle your library.");
			this.addCost(removeCountersFromThis(5, Counter.CounterType.QUEST, "Quest for the Holy Relic"));

			this.addCost(sacrificeThis("Quest for the Holy Relic"));

			EventFactory effect = new EventFactory(QUEST_FOR_THE_HOLY_RELIC_EVENT, "Search your library for an Equipment card, put it onto the battlefield, and attach it to a creature you control. Then shuffle your library.");
			effect.parameters.put(EventType.Parameter.CAUSE, This.instance());
			effect.parameters.put(EventType.Parameter.PLAYER, You.instance());
			effect.parameters.put(EventType.Parameter.CHOICE, CREATURES_YOU_CONTROL);
			this.addEffect(effect);
		}
	}

	public QuestfortheHolyRelic(GameState state)
	{
		super(state);

		// Whenever you cast a creature spell, you may put a quest counter on
		// Quest for the Holy Relic.
		this.addAbility(new CreatureQuest(state));

		// Remove five quest counters from Quest for the Holy Relic and
		// sacrifice it: Search your library for an Equipment card, put it onto
		// the battlefield, and attach it to a creature you control. Then
		// shuffle your library.
		this.addAbility(new FindEquipment(state));
	}
}
