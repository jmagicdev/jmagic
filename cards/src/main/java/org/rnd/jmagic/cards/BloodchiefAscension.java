package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.engine.patterns.*;

@Name("Bloodchief Ascension")
@Types({Type.ENCHANTMENT})
@ManaCost("B")
@Printings({@Printings.Printed(ex = Expansion.ZENDIKAR, r = Rarity.RARE)})
@ColorIdentity({Color.BLACK})
public final class BloodchiefAscension extends Card
{
	public static final class QuestCounters extends EventTriggeredAbility
	{
		public QuestCounters(GameState state)
		{
			super(state, "At the beginning of each end step, if an opponent lost 2 or more life this turn, you may put a quest counter on Bloodchief Ascension.");
			this.addPattern(atTheBeginningOfTheEndStep());

			state.ensureTracker(new LifeLostThisTurn.LifeTracker());
			SetGenerator lifeEachOpponentLost = LifeLostThisTurn.instance(OpponentsOf.instance(You.instance()));
			this.interveningIf = Intersect.instance(Between.instance(2, null), lifeEachOpponentLost);

			this.addEffect(youMayPutAQuestCounterOnThis("Bloodchief Ascension"));
		}
	}

	/**
	 * @eparam CAUSE: Bloodchief Ascension's second ability
	 * @eparam TARGET: the opponent losing life
	 * @eparam PLAYER: the controller of CAUSE
	 * @eparam RESULT: empty
	 */
	public static final EventType BLOODCHIEF_DRAIN = new EventType("BLOODCHIEF_DRAIN")
	{
		@Override
		public Parameter affects()
		{
			return null;
		}

		@Override
		public boolean attempt(Game game, Event event, java.util.Map<Parameter, Set> parameters)
		{
			Set opponent = parameters.get(Parameter.TARGET);
			java.util.Map<Parameter, Set> lossParameters = new java.util.HashMap<Parameter, Set>();
			lossParameters.put(Parameter.CAUSE, parameters.get(Parameter.CAUSE));
			lossParameters.put(Parameter.PLAYER, opponent);
			lossParameters.put(Parameter.NUMBER, new Set(2));
			Event loss = createEvent(game, opponent + " loses 2 life", LOSE_LIFE, lossParameters);
			return loss.attempt(event);
		}

		@Override
		public boolean perform(Game game, Event event, java.util.Map<Parameter, Set> parameters)
		{
			Set cause = parameters.get(Parameter.CAUSE);
			Set opponent = parameters.get(Parameter.TARGET);
			Set you = parameters.get(Parameter.PLAYER);

			java.util.Map<Parameter, Set> lossParameters = new java.util.HashMap<Parameter, Set>();
			lossParameters.put(Parameter.CAUSE, cause);
			lossParameters.put(Parameter.PLAYER, opponent);
			lossParameters.put(Parameter.NUMBER, new Set(2));
			Event loss = createEvent(game, opponent + " loses 2 life", LOSE_LIFE, lossParameters);
			loss.perform(event, true);

			java.util.Map<Parameter, Set> gainParameters = new java.util.HashMap<Parameter, Set>();
			gainParameters.put(Parameter.CAUSE, cause);
			gainParameters.put(Parameter.PLAYER, you);
			gainParameters.put(Parameter.NUMBER, new Set(2));
			Event gain = createEvent(game, "You gain 2 life", GAIN_LIFE, gainParameters);
			gain.perform(event, true);

			return true;
		}
	};

	public static final class LifeDrain extends EventTriggeredAbility
	{
		public LifeDrain(GameState state)
		{
			super(state, "Whenever a card is put into an opponent's graveyard from anywhere, if Bloodchief Ascension has three or more quest counters on it, you may have that player lose 2 life. If you do, you gain 2 life.");

			this.addPattern(new SimpleZoneChangePattern(null, GraveyardOf.instance(OpponentsOf.instance(You.instance())), Cards.instance(), false));

			SetGenerator questCountersOnThis = CountersOn.instance(ABILITY_SOURCE_OF_THIS, Counter.CounterType.QUEST);
			this.interveningIf = Intersect.instance(Between.instance(3, null), Count.instance(questCountersOnThis));

			SetGenerator thatGraveyard = DestinationZoneOf.instance(TriggerZoneChange.instance(This.instance()));
			SetGenerator thatPlayer = OwnerOf.instance(thatGraveyard);

			EventFactory drain = new EventFactory(BLOODCHIEF_DRAIN, "That player loses 2 life and you gain 2 life");
			drain.parameters.put(EventType.Parameter.CAUSE, This.instance());
			drain.parameters.put(EventType.Parameter.TARGET, thatPlayer);
			drain.parameters.put(EventType.Parameter.PLAYER, You.instance());

			this.addEffect(youMay(drain, "You may have that player lose 2 life.  If you do, you gain 2 life."));
		}
	}

	public BloodchiefAscension(GameState state)
	{
		super(state);

		// At the beginning of each end step, if an opponent lost 2 or more life
		// this turn, you may put a quest counter on Bloodchief Ascension.
		// (Damage causes loss of life.)
		this.addAbility(new QuestCounters(state));

		// Whenever a card is put into an opponent's graveyard from anywhere, if
		// Bloodchief Ascension has three or more quest counters on it, you may
		// have that player lose 2 life. If you do, you gain 2 life.
		this.addAbility(new LifeDrain(state));
	}
}
